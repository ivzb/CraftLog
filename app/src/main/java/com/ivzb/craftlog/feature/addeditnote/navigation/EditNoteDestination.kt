package com.ivzb.craftlog.feature.addeditnote.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.addeditnote.AddEditNoteRoute
import com.ivzb.craftlog.feature.notes.NOTE
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination
import com.ivzb.craftlog.navigation.getItem

object EditNoteDestination : CraftLogNavigationDestination {

    override val route = "edit_note_route"

    override val destination = "edit_note_destination"

}

fun NavGraphBuilder.editNoteGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {
    composable(route = EditNoteDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        val note = navController.getItem<Note>(NOTE)

        AddEditNoteRoute(note, navController)
    }
}
