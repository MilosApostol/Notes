package com.notes.database.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notes.database.models.NotesEntity
import com.notes.database.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _allNotes = MutableStateFlow<List<NotesEntity>>(emptyList())
    val allNotes: StateFlow<List<NotesEntity>> = _allNotes
    fun getNoteById(id: Int): Flow<NotesEntity?> = notesRepository.getNoteById(id)


    init {
        fetchAllNotes()
    }

    private fun fetchAllNotes() {
        viewModelScope.launch {
            notesRepository.getNotes().collect { notes ->
                _allNotes.value = notes
            }
        }
    }

    fun deleteNoteById(id: Int) {
        viewModelScope.launch {
            notesRepository.deleteNoteById(id)
        }
    }

    fun insertNotes(note: NotesEntity, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isInserted = notesRepository.insertNotes(note)
            onSuccess(isInserted)
        }
    }
}