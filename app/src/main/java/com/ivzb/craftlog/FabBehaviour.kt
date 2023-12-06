package com.ivzb.craftlog

import androidx.compose.ui.graphics.vector.ImageVector

data class FabBehaviour(
    val visibility: Boolean,
    val textId: Int,
    val icon: ImageVector,
    val analyticsEvent: String,
    val destinationRoute: String,
)