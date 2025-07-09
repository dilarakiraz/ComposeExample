package com.dilara.composeexample.components.divider

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dilara.composeexample.ui.theme.ComposeExampleTheme

@Composable
fun PortfolioSectionedCardWithDividerExample() {
    val portfolio = listOf(
        PortfolioStock("AKBNK", 100, 41.25, 5.2),
        PortfolioStock("THYAO", 50, 315.10, -2.1),
        PortfolioStock("SISE", 200, 56.30, 0.0)
    )
    val totalValue = portfolio.sumOf { it.amount * it.price }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(22.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Toplam Portföy Değeri",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = String.format("%.2f TL", totalValue),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                thickness = 1.5.dp
            )
            Spacer(modifier = Modifier.height(10.dp))
            portfolio.forEachIndexed { idx, stock ->
                PortfolioStockRow(stock)
                if (idx != portfolio.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

data class PortfolioStock(val code: String, val amount: Int, val price: Double, val change: Double)

@Composable
fun PortfolioStockRow(stock: PortfolioStock) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stock.code,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${stock.amount} adet",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = String.format("%.2f TL", stock.price),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
        PortfolioChangeBadge(change = stock.change)
    }
}

@Composable
fun PortfolioChangeBadge(change: Double) {
    val (color, text) = when {
        change > 0 -> Pair(Color(0xFF4CAF50), "+%.2f%%".format(change))
        change < 0 -> Pair(Color(0xFFE53935), "%.2f%%".format(change))
        else -> Pair(Color.Gray, "%.2f%%".format(change))
    }
    Surface(
        color = color.copy(alpha = 0.13f),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(26.dp)
            .widthIn(min = 48.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Preview(showBackground = true, name = "Portfolio Sectioned Card Preview")
@Composable
fun PreviewPortfolioSectionedCardWithDividerExample() {
    ComposeExampleTheme {
        Box(Modifier.height(300.dp)) {
            PortfolioSectionedCardWithDividerExample()
        }
    }
} 