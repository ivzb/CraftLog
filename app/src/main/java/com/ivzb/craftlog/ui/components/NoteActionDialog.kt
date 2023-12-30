package com.ivzb.craftlog.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ivzb.craftlog.R

@Composable
fun NoteActionDialog(
    title: String,
    onDismissRequest: () -> Unit,
    onCopy: () -> Unit,
    onShare: () -> Unit,
    onDelete: () -> Unit,
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

                HorizontalDivider(modifier = Modifier.padding(16.dp, 0.dp), color = Color.LightGray, thickness = 1.dp)

                Item(R.string.copy, R.drawable.ic_copy, onCopy)

                HorizontalDivider(modifier = Modifier.padding(16.dp, 0.dp), color = Color.LightGray, thickness = 1.dp)

                Item(R.string.share, R.drawable.ic_share, onShare)

                HorizontalDivider(modifier = Modifier.padding(16.dp, 0.dp), color = Color.LightGray, thickness = 1.dp)

                Item(R.string.delete, R.drawable.ic_delete, onDelete)
            }
        }
    }
}

@Composable
private fun Item(textId: Int, iconId: Int, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(
                color = Color.Black
            ),
            onClick = {
                onClick()
            }
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = stringResource(id = textId),
            )
        }
    }
}