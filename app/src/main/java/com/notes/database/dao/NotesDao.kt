package com.notes.database.dao

import androidx.room.*
import com.notes.database.models.NotesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    //if we call INSERT function with an existing id, it will update the existing entry
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NotesEntity)


    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): Flow<List<NotesEntity>>

    @Query("SELECT * FROM notes_table WHERE id = :id")
    fun getNoteById(id: Int): Flow<NotesEntity>

    @Query("DELETE FROM notes_table")
    suspend fun deleteNotes()

    @Query("DELETE FROM notes_table WHERE id = :id")
    suspend fun deleteNoteById(id: Int)
}