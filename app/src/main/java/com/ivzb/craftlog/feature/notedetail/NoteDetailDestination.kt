package com.ivzb.craftlog.feature.notedetail

import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.notes.NOTE
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination

object NoteDetailDestination : CraftLogNavigationDestination {

    override val route = "note_detail_route"
    override val destination = "note_detail_destination"

}

fun NavGraphBuilder.noteDetailGraph(
    navController: NavController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
    onBackClicked: () -> Unit
) {

    composable(
        route = NoteDetailDestination.route,
    ) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }
        val noteBundle = navController.previousBackStackEntry?.savedStateHandle?.get<Bundle>(
            NOTE
        )
        val note = noteBundle?.getParcelable<Note>(NOTE)
        NoteDetailRoute(note, onBackClicked)
    }
}
