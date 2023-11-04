package com.example.notesroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
class Note {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "title")
    var title: String

    @ColumnInfo(name = "content")
    var content: String

    constructor(id: Int, title: String, content: String) {
        this.id = id
        this.title = title
        this.content = content
    }

    @Ignore
    constructor(title: String, content: String) {
        this.title = title
        this.content = content
    }

}