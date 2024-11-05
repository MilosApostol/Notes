package com.notes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.notes.database.dao.NotesDao
import com.notes.database.models.NotesEntity


@Database(entities = [NotesEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao


    companion object {
        val DATABASE = "database"
    }
}