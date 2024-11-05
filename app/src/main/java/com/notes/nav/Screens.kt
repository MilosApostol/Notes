package com.notes.nav

enum class Screens(val route: String) {
    HomeScreen("HomeScreen"),
    AddNoteScreen("AddNoteScreen?noteId={noteId}")
}
