package com.example.notesroom

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val context: Context, private val arrayNotes: ArrayList<Note>, private val databaseHelper: DatabaseHelper): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.note_view, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(arrayNotes[position], position)
    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        private val txtContent: TextView = itemView.findViewById(R.id.txtContent)
        private val llClick: LinearLayout = itemView.findViewById(R.id.llClick)

        fun bind(note: Note, position: Int) {
            txtTitle.text = note.title
            txtContent.text = note.content

            llClick.setOnLongClickListener{
                deleteNote(position)
                true
            }
        }

        private fun deleteNote(position: Int) {
            var dialog = AlertDialog.Builder(context)
                .setTitle("Delete !")
                .setMessage("Do you want to delete the note")
                .setPositiveButton("Yes"){ dialog, which->
                    databaseHelper.noteDao().deleteNote(
                        Note(
                            arrayNotes[position].id,
                            arrayNotes[position].title,
                            arrayNotes[position].content
                        ))
                    (context as MainActivity).showNotes()
                }.setNegativeButton("No") {dialog, which->
                }.create().show()
        }
    }
}

