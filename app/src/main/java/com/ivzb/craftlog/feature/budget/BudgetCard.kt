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
import java.math.BigDecimal
import java.text.DateFormatSymbols

@Composable
fun BudgetCard(
    budget: Budget,
    navigateToBudgetDetail: (Budget) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navigateToBudgetDetail(budget)
            },
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // todo: make all budget computed fields automatic - create sheet for each column dynamically - don't hardcode them

            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(8.dp, 0.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "${budget.month.toFormattedMonth()}, ${budget.year}",
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.income),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budget.income.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.saved),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budget.saved.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.cost_of_living),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budget.costOfLiving.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.investments),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budget.investments.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.bank_start),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budget.bankStart.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

            }

            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(8.dp, 0.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "",
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.spent),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budget.spent.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.balance),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budget.balance.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.emergency_fund),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budget.emergencyFund.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.mortgage),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budget.mortgage.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.bank_end),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = budget.bankEnd.toPlainString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun BudgetCardPreview() {
    BudgetCard(
        Budget(
            year = 2023,
            month = 11,
            income = BigDecimal.ZERO,
            spent = BigDecimal.ZERO,
            saved = BigDecimal.ZERO
        )
    ) { }
}

private fun Int.toFormattedMonth(): String {
    return DateFormatSymbols().months[this]
}