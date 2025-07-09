package com.dilara.composeexample.components.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MarketNewsWithStickyHeaderAndDividerExample() {
    val newsGroups = listOf(
        NewsGroup(
            date = "9 Temmuz 2025",
            news = listOf(
                NewsItem("BIST100 rekor tazeledi", "Borsa İstanbul 100 endeksi yeni zirveye ulaştı."),
                NewsItem("Dolar/TL yatay seyretti", "Döviz piyasasında bugün önemli bir hareketlilik yaşanmadı.")
            )
        ),
        NewsGroup(
            date = "8 Temmuz 2025",
            news = listOf(
                NewsItem("Bankacılık hisseleri yükseldi", "Bankacılık sektörü hisselerinde güçlü alımlar görüldü."),
                NewsItem("Altın fiyatları geriledi", "Ons altın fiyatı gün içinde %1 değer kaybetti.")
            )
        )
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        newsGroups.forEach { group ->
            item {
                NewsHeaderCard(date = group.date)
            }
            itemsIndexed(group.news) { idx, news ->
                NewsItemCard(news)
                if (idx != group.news.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 18.dp),
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.13f),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

data class NewsGroup(val date: String, val news: List<NewsItem>)
data class NewsItem(val title: String, val summary: String)

@Composable
fun NewsHeaderCard(date: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 18.dp)
        )
    }
}

@Composable
fun NewsItemCard(news: NewsItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = news.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, name = "Market News Sticky Header Preview")
@Composable
fun PreviewMarketNewsWithStickyHeaderAndDividerExample() {
    MaterialTheme {
        MarketNewsWithStickyHeaderAndDividerExample()
    }
} 