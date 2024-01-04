package com.ivzb.craftlog.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ivzb.craftlog.extenstion.toRelativeDateString
import java.math.BigDecimal
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.ListHeader(title: String, total: BigDecimal? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier
                .animateItemPlacement()
                .padding(3.dp, 12.dp, 8.dp, 0.dp),
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )

        if (total != null) {
            Text(
                modifier = Modifier
                    .animateItemPlacement()
                    .padding(3.dp, 12.dp, 8.dp, 0.dp),
                text = total.toPlainString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}