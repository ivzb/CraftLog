package com.ivzb.craftlog.feature.notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.R
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.addeditnote.navigation.AddNoteDestination
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

const val NOTE = "note"

object NotesDestination : CraftLogNavigationDestination {

    override val route = "notes_route"

    override val destination = "notes_destination"

}

fun NavGraphBuilder.notesGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {
    composable(route = NotesDestination.route) {
        LaunchedEffect(Unit) {
            bottomBarVisibility.value = true
            fabBehaviour.value = FabBehaviour(
                visibility = true,
                textId = R.string.add_note,
                icon = Icons.Default.Add,
                analyticsEvent = AnalyticsEvents.ADD_NOTE_CLICKED_FAB,
                destinationRoute = AddNoteDestination.route,
            )
        }

        NotesRoute(navController)
    }
}
