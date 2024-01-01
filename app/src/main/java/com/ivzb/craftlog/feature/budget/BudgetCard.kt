package com.ivzb.craftlog.feature.budget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ivzb.craftlog.R
import com.ivzb.craftlog.domain.model.Budget
import com.ivzb.craftlog.domain.model.BudgetOverview
import java.math.BigDecimal
import java.text.DateFormatSymbols

@Composable
fun BudgetCard(
    budgetOverview: BudgetOverview,
    navigateToBudgetDetail: (Budget) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navigateToBudgetDetail(budgetOverview.budget)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, end = 24.dp, bottom = 16.dp, start = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                style = MaterialTheme.typography.titleMedium,
                text = "${budgetOverview.budget.month.toFormattedMonth()}, ${budgetOverview.budget.year}",
                color = MaterialTheme.colorScheme.primary
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }

        Row(
            modifier = Modifier.padding(end = 16.dp, bottom = 16.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(8.dp, 0.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = stringResource(id = R.string.income),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budgetOverview.budget.income?.toPlainString() ?: "-",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.saved),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budgetOverview.saved.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

            }

            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(8.dp, 0.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = stringResource(id = R.string.balance),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budgetOverview.balance.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.spent),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budgetOverview.spent.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

            }

            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(8.dp, 0.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = stringResource(id = R.string.bank_start),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budgetOverview.budget.bankStart?.toPlainString() ?: "-",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.bank_end),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budgetOverview.budget.bankEnd?.toPlainString() ?: "-",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

            }
        }
    }
}

@Preview
@Composable
private fun BudgetCardPreview() {
    BudgetCard(
        BudgetOverview(
            Budget(0, 2023, 12, null, null, null),
            listOf(),
            listOf()
        )
    ) { }
}

private fun Int.toFormattedMonth(): String {
    return DateFormatSymbols().months[this]
}