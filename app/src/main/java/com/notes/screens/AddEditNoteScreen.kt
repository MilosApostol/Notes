package com.notes.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.notes.utils.NotesHelper.handleSaveNote
import com.notes.R
import com.notes.custom.AddEditNoteContent
import com.notes.database.viewmodel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel(),
    noteId: Int = -1
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val currentTime = remember {
        SimpleDateFormat("MMMM d, yyyy, HH:mm", Locale.getDefault()).format(Date())
    }
    var id = -1
    val context = LocalContext.current
    val topBarTitle =
        if (noteId == -1) stringResource(R.string.add_note) else stringResource(R.string.edit_note)
    Log.d("NoteId", noteId.toString())
    LaunchedEffect(key1 = noteId) {
        if (noteId != -1) {
            viewModel.getNoteById(noteId).collect { note ->
                note?.let {
                    title = it.title
                    description = it.description
                    id = noteId
                }
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text(topBarTitle) }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            handleSaveNote(
                title = title,
                description = description,
                id = id,
                context = context,
                viewModel = viewModel,
                navController = navController
            )
        }) {
            Icon(Icons.Filled.Done, contentDescription = "Save Note")
        }
    }, containerColor = Color.White, content = { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current) / 2,
                    top = innerPadding.calculateTopPadding() / 2,
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current) / 2,
                    bottom = innerPadding.calculateBottomPadding() / 2
                )
        ) {
            AddEditNoteContent(
                title = title,
                description = description,
                onTitleChange = { title = it },
                onDescriptionChange = { description = it },
                currentTime = currentTime
            )
        }
    })
}
