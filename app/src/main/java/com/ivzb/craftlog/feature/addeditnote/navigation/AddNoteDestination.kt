package com.ivzb.craftlog.feature.addeditnote.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.feature.addeditnote.AddEditNoteRoute
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object AddNoteDestination : CraftLogNavigationDestination {

    override val route = "add_note_route"

    override val destination = "add_note_destination"

}

fun NavGraphBuilder.addNoteGraph(
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    navigateBack: () -> Unit,
    navigateToNotes: () -> Unit,
) {
    composable(route = AddNoteDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        AddEditNoteRoute(null, navigateBack, navigateToNotes)
    }
}
