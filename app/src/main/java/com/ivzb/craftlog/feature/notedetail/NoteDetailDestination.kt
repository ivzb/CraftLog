package com.ivzb.craftlog.feature.notedetail

import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ivzb.craftlog.FabBehaviour
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.notes.NOTE
import com.ivzb.craftlog.navigation.CraftLogNavigationDestination
import com.ivzb.craftlog.navigation.getItem
import com.ivzb.craftlog.util.getParcelable

object NoteDetailDestination : CraftLogNavigationDestination {

    override val route = "note_detail_route"
    override val destination = "note_detail_destination"

}

fun NavGraphBuilder.noteDetailGraph(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    fabBehaviour: MutableState<FabBehaviour?>,
) {

    composable(
        route = NoteDetailDestination.route,
    ) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabBehaviour.value = null
        }

        val note = navController.getItem<Note>(NOTE)

        NoteDetailRoute(navController, note)
    }
}
