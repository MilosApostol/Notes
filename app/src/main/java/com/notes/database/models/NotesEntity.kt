package com.notes.database.models

import androidx.room.*


@Entity(tableName = "notes_table")
data class NotesEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val title: String,
    val description: String,
    val timestamp: Long
)