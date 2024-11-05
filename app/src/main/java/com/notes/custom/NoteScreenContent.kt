package com.notes.custom


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.notes.database.models.NotesEntity
import com.notes.database.viewmodel.NotesViewModel

@Composable
fun NoteScreenContent(
    paddingValues: PaddingValues,
    navController: NavController,
    notes: List<NotesEntity>,
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    var updatingNotes by remember { mutableStateOf(notes) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(notes) { note ->
            SwipeToDismissNoteField(title = note.title,
                description = note.description,
                date = note.timestamp,
                id = note.id ?: 0,
                onClick = { noteId ->
                    navController.navigate("AddNoteScreen?noteId=$noteId")
                },
                onRemove = {
                    note.id?.let { noteId ->
                        updatingNotes = updatingNotes.filterNot { it.id == noteId }
                        notesViewModel.deleteNoteById(noteId)
                    }
                })
        }
    }
}