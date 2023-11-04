package com.example.notesroom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Query("Select * from notes")
    fun getNotes(): List<Note>

    @Insert
    fun addNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}