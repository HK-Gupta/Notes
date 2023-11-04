package com.example.notesroom

import android.app.Dialog
import android.health.connect.datatypes.units.Length
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var btnCreate: Button
    lateinit var fabAdd: FloatingActionButton
    lateinit var recyclerNotes: RecyclerView
    lateinit var databaseHelper: DatabaseHelper
    lateinit var emptyLL: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeVariables()
        showNotes()

        fabAdd.setOnClickListener {
            var dialog = Dialog(this@MainActivity)
            dialog.setContentView(R.layout.add_note_layout)

            val edtTitle = dialog.findViewById<EditText>(R.id.edtTitle)
            val edtContent = dialog.findViewById<EditText>(R.id.edtContent)
            val btnAdd = dialog.findViewById<Button>(R.id.btnAdd)

            btnAdd.setOnClickListener {
                val title = edtTitle.text.toString()
                val content = edtContent.text.toString()
                if(title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(this, "Please fill all the Fields", Toast.LENGTH_SHORT).show()
                } else {
                    databaseHelper.noteDao().addNote(Note(title, content))
                    showNotes()

                    dialog.dismiss()
                }
            }
            dialog.show()
        }

        btnCreate.setOnClickListener {
            fabAdd.performClick()
        }

    }

    fun showNotes() {
        var arrayNotes: ArrayList<Note> = databaseHelper.noteDao().getNotes() as ArrayList<Note>
        if(arrayNotes.isEmpty()) {
            emptyLL.visibility = View.VISIBLE
            recyclerNotes.visibility = View.GONE
        } else{
            recyclerNotes.visibility = View.VISIBLE
            emptyLL.visibility = View.GONE

            recyclerNotes.adapter = NoteAdapter(this, arrayNotes, databaseHelper)
        }
    }

    private fun initializeVariables() {
        btnCreate = findViewById(R.id.btnCreate)
        fabAdd = findViewById(R.id.fabAdd)
        emptyLL = findViewById(R.id.emptyLL)
        recyclerNotes = findViewById(R.id.recyclerNotes)

        recyclerNotes.layoutManager = GridLayoutManager(this, 2)

        databaseHelper = DatabaseHelper.getInstance(this)
    }

}