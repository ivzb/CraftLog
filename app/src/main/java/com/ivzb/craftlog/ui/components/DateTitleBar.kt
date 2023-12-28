package com.ivzb.craftlog.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ivzb.craftlog.extenstion.toRelativeDateString
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.DateTitleBar(time: Long) {
    Text(
        modifier = Modifier
            .animateItemPlacement()
            .padding(3.dp, 12.dp, 8.dp, 0.dp),
        text = Date(time).toRelativeDateString(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary
    )
}