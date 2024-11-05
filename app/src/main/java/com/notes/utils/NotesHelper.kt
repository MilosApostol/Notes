package com.notes.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavHostController
import com.notes.R
import com.notes.database.models.NotesEntity
import com.notes.database.viewmodel.NotesViewModel
import com.notes.nav.Screens

object NotesHelper {

    fun handleSaveNote(
        title: String,
        description: String,
        id: Int,
        context: Context,
        viewModel: NotesViewModel,
        navController: NavHostController
    ) {
        try {
            if (title.isEmpty() || description.isEmpty()) {
                val message = context.getString(R.string.title_or_description_is_empty)
                Log.e(context.getString(R.string.addnotescreen), message)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                return
            }

            val note = NotesEntity(
                title = title,
                description = description,
                id = if (id != -1) id else null,
                timestamp = System.currentTimeMillis()
            )

            viewModel.insertNotes(note) { isInserted ->
                val logMessage = if (isInserted) {
                    context.getString(R.string.added_note_successfully)
                } else {
                    context.getString(R.string.failed_to_add_note)
                }
                Log.e(context.getString(R.string.addnotescreen), logMessage)
                if (isInserted) {
                    navController.navigate(Screens.HomeScreen.name)
                } else {
                    Toast.makeText(context, logMessage, Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            val errorMessage = context.getString(
                R.string.error_adding_note,
                e.localizedMessage ?: context.getString(R.string.unknown_error)
            )
            Log.e(context.getString(R.string.addnotescreen), errorMessage)
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}