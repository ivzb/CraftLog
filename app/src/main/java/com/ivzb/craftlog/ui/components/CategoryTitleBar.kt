package com.ivzb.craftlog.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ivzb.craftlog.R

@Composable
fun CategoryTitleBar(
    title: String,
    showAddButton: Boolean = true,
    showMoreButton: Boolean = true,
    onAddClick: (() -> Unit)? = null,
    onMoreClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            verticalAlignment = Alignment.Bottom
        ) {

            Text(
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.titleLarge,
                text = title,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.padding(4.dp))

            if (showAddButton) {
                Icon(
                    modifier = Modifier
                        .padding(bottom = 2.dp)
                        .clickable {
                            onAddClick?.invoke()
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add_circle_outline),
                    contentDescription = stringResource(R.string.add),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        if (showMoreButton) {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable {
                        onMoreClick?.invoke()
                    },
                style = MaterialTheme.typography.labelLarge,
                text = stringResource(id = R.string.more),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}