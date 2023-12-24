package com.ivzb.craftlog.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ivzb.craftlog.R

@Composable
fun CategoryTitleBar(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            text = title,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.labelLarge,
            text = stringResource(id = R.string.more),
            color = MaterialTheme.colorScheme.primary
        )
    }
}