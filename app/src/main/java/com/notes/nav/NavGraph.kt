package com.notes.nav


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.notes.screens.AddEditNoteScreen
import com.notes.screens.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController, modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route,
    ) {
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController = navController, modifier = modifier)
        }
        composable(
            route = Screens.AddNoteScreen.route,
            arguments = listOf(
                navArgument(name = "noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
            AddEditNoteScreen(
                modifier = modifier,
                navController = navController,
                noteId = noteId
            )
        }
    }
}