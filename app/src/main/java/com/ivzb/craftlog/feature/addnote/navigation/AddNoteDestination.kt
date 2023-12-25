package com.ivzb.craftlog.feature.addnote.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.feature.addnote.AddNoteRoute
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object AddNoteDestination : CraftLogNavigationDestination {

    override val route = "add_note_route"

    override val destination = "add_note_destination"

}

fun NavGraphBuilder.addNoteGraph(
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    onBackClicked: () -> Unit,
    navigateToNotes: () -> Unit,
) {
    composable(route = AddNoteDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        AddNoteRoute(onBackClicked, navigateToNotes)
    }
}
