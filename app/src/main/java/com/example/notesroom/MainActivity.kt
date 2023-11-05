package com.example.notesroom

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var btnCreate: Button
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var recyclerNotes: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var emptyLL: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Changing color of action bar and status bar
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.action_bar))
        supportActionBar?.title = "Notes"
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)


        initializeVariables()
        showNotes()

        fabAdd.setOnClickListener {
            val dialog = Dialog(this@MainActivity)
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
        val arrayNotes: ArrayList<Note> = databaseHelper.noteDao().getNotes() as ArrayList<Note>
        if(arrayNotes.isEmpty()) {
            // when there is no note available then Linear layout will be present otherwise the Relative layout will be there
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

        recyclerNotes.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        databaseHelper = DatabaseHelper.getInstance(this)
    }

}