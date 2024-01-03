package com.ivzb.craftlog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ActionDialog(
    title: String,
    onDismissRequest: () -> Unit,
    actionItems: List<@Composable () -> Unit>
) {
    val backgroundColor = Color.White

    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(backgroundColor)
        ) {

            Column {

                Text(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                actionItems.forEach { actionItem ->
                    HorizontalDivider(modifier = Modifier.padding(16.dp, 0.dp), color = Color.LightGray, thickness = 1.dp)

                    actionItem()
                }
            }
        }
    }
}