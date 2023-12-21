package com.ivzb.craftlog.feature.investments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.extenstion.toFormattedDateString
import com.ivzb.craftlog.extenstion.toRelativeDateString
import com.ivzb.craftlog.util.InvestmentCategory
import java.util.Date

@Composable
fun InvestmentCard(
    modifier: Modifier = Modifier,
    investment: Investment,
    navigateToInvestmentDetail: (Investment) -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navigateToInvestmentDetail(investment)
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

            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(8.dp, 0.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = MaterialTheme.typography.titleSmall,
                    text = investment.date.toRelativeDateString(),
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "${investment.name} - ${investment.amount} - ${investment.cost}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
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
        )
    ) { }
}
