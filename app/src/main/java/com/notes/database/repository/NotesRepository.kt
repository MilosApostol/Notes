package com.notes.database.repository

import com.notes.database.dao.NotesDao
import com.notes.database.models.NotesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NotesRepository @Inject constructor(private val dao: NotesDao) {

    fun getNotes() = dao.getAllNotes()

    fun getNoteById(id: Int): Flow<NotesEntity?> = dao.getNoteById(id)

    suspend fun insertNotes(note: NotesEntity): Boolean {
        return try {
            dao.insertNote(note)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteNotes() {
        dao.deleteNotes()
    }

    suspend fun deleteNoteById(id: Int) {
        dao.deleteNoteById(id)
    }
}