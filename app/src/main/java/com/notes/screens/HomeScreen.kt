package com.notes.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.notes.custom.*
import com.notes.database.models.NotesEntity
import com.notes.database.viewmodel.NotesViewModel
import com.notes.nav.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    var search by remember { mutableStateOf("") }

    val notesState = notesViewModel.allNotes.collectAsState(initial = emptyList())

    val filteredNotes = notesState.value.filter { note ->
        note.title.contains(search, ignoreCase = true)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CTopBar(scrollBehavior = scrollBehavior,
                searchText = search,
                onSearchTextChange = { newText -> search = newText })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screens.AddNoteScreen.name)
            }) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        },
    ) { innerPadding ->
        NoteScreenContent(
            paddingValues = innerPadding, navController = navController, notes = filteredNotes,
        )

    }
}
