package com.example.notesroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class  DatabaseHelper: RoomDatabase() {

    companion object {
        private const val DB_NAME = "notes_db"
        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context):DatabaseHelper {
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseHelper::class.java,
                    DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
    abstract fun noteDao(): NoteDao
}