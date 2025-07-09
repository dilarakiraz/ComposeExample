package com.dilara.composeexample.components.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun StockListWithDividerExample() {
    val stocks = listOf(
        Stock("AKBNK", 41.25, 1.8),
        Stock("THYAO", 315.10, -0.7),
        Stock("ASELS", 78.90, 0.0),
        Stock("SISE", 56.30, 2.3),
        Stock("BIMAS", 246.50, -1.2)
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(stocks) { stock ->
            StockCard(stock)
            if (stock != stocks.last()) {
                Divider(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    thickness = 1.2.dp
                )
            }
        }
    }
}

data class Stock(val code: String, val price: Double, val change: Double)

@Composable
fun StockCard(stock: Stock) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stock.code,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = String.format("%.2f TL", stock.price),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(end = 12.dp)
            )
            StockChangeBadge(change = stock.change)
        }
    }
}

@Composable
fun StockChangeBadge(change: Double) {
    val (color, text) = when {
        change > 0 -> Pair(Color(0xFF4CAF50), "+%.2f%%".format(change))
        change < 0 -> Pair(Color(0xFFE53935), "%.2f%%".format(change))
        else -> Pair(Color.Gray, "%.2f%%".format(change))
    }
    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(28.dp)
            .widthIn(min = 54.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true, name = "Stock List Divider Preview")
@Composable
fun PreviewStockListWithDividerExample() {
    MaterialTheme {
        Box(Modifier.height(400.dp)) {
            StockListWithDividerExample()
        }
    }
} 