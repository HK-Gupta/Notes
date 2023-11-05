package com.example.notesroom

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class NoteAdapter(private val context: Context, private val arrayNotes: ArrayList<Note>, private val databaseHelper: DatabaseHelper): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.note_view, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(arrayNotes[position], position, holder)
    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        private val txtContent: TextView = itemView.findViewById(R.id.txtContent)
        private val llClick: LinearLayout = itemView.findViewById(R.id.llClick)

        fun bind(note: Note, position: Int, holder: NoteViewHolder) {
            txtTitle.text = note.title
            txtContent.text = note.content

            val colorCode = getRandomColor()
            holder.llClick.setBackgroundColor(ContextCompat.getColor(context, colorCode))


            llClick.setOnLongClickListener{
                deleteNote(position)
                true
            }


        }

        private fun deleteNote(position: Int) {
            AlertDialog.Builder(context)
                .setTitle("Delete !")
                .setMessage("Do you want to delete the note")
                .setPositiveButton("Yes"){ _, _->
                    databaseHelper.noteDao().deleteNote(
                        Note(
                            arrayNotes[position].id,
                            arrayNotes[position].title,
                            arrayNotes[position].content
                        ))
                    (context as MainActivity).showNotes()
                }.setNegativeButton("No") {_, _->
                }.create().show()
        }
        private fun getRandomColor(): Int {
            val colorCode:List<Int> = listOf(
                R.color.color1,
                R.color.color2,
                R.color.color3,
                R.color.color4,
                R.color.color5,
                R.color.color6,
                R.color.color7,
                R.color.color8)

            val random = Random
            val number = random.nextInt(colorCode.size)
            return colorCode[number]
        }
    }

}

