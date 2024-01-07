package com.ivzb.craftlog.feature.investments

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ivzb.craftlog.R
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.ui.components.ActionDialog
import com.ivzb.craftlog.ui.components.ActionItem
import com.ivzb.craftlog.util.InvestmentCategory
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InvestmentCard(
    modifier: Modifier = Modifier,
    investment: Investment,
    onInvestmentDetail: (Investment) -> Unit,
    onEdit: (Investment) -> Unit,
    onDelete: (Investment) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    val showActionDialog = {
        showDialog = true
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .combinedClickable(
                onClick = {
                    onInvestmentDetail(investment)
                },
                onLongClick = {
                    showActionDialog()
                },
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(8.dp, 0.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "${investment.name} (${investment.amount})",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = investment.cost.toPlainString(),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )

            }
        }
    }

    if (showDialog) {
        ActionDialog(
            title = investment.name,
            onDismissRequest = { showDialog = false },
            actionItems = listOf(
                {
                    ActionItem(R.string.edit, R.drawable.ic_edit) {
                        onEdit(investment)
                        showDialog = false
                    }
                },
                {
                    ActionItem(R.string.delete, R.drawable.ic_delete) {
                        onDelete(investment)
                        showDialog = false
                    }
                },
            )
        )
    }
}

@Preview
@Composable
private fun InvestmentCardPreview() {
    InvestmentCard(
        Modifier,
        Investment(
            id = 123L,
            name = "dinner",
            amount = 12.5.toBigDecimal(),
            cost = 50.toBigDecimal(),
            categoryId = InvestmentCategory.Stocks.id,
            date = Date(),
            additionalData = mapOf()
        ),
        onInvestmentDetail = { },
        onEdit = { },
        onDelete = { }
    )
}
