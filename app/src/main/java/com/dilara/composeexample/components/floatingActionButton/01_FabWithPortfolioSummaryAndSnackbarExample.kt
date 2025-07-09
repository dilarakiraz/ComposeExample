package com.dilara.composeexample.components.floatingActionButton

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dilara.composeexample.ui.theme.ComposeExampleTheme

@Composable
fun FabWithPortfolioSummaryAndSnackbarExample() {
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val notificationCount = 2
    val portfolioValue = 185_430.75
    val profit = 7.8
    val sectors = listOf("Tümü", "Banka", "Teknoloji", "Enerji", "Gıda", "Savunma", "Perakende")
    var selectedSector by remember { mutableStateOf(sectors[0]) }
    val allStocks = listOf(
        Stock("AKBNK", 41.25, 1.8, "Banka", true),
        Stock("THYAO", 315.10, -0.7, "Havacılık", false),
        Stock("ASELS", 78.90, 0.0, "Savunma", true),
        Stock("SISE", 56.30, 2.3, "Cam", false),
        Stock("BIMAS", 246.50, -1.2, "Perakende", false),
        Stock("KRDMD", 22.10, 3.1, "Enerji", false),
        Stock("VESTL", 98.40, 0.9, "Teknoloji", true)
    )
    val stocks = if (selectedSector == "Tümü") allStocks else allStocks.filter { it.sector == selectedSector }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF4F8CFF), Color(0xFFEEF3FA)),
                    startY = 0f,
                    endY = 900f
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 20.dp, end = 20.dp, bottom = 12.dp)
                    .clip(MaterialTheme.shapes.extraLarge),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.95f)
                ),
                elevation = CardDefaults.cardElevation(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Portföyüm",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF4F8CFF)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = String.format("%.2f TL", portfolioValue),
                            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF222B45)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Getiri: %+.2f%%".format(profit),
                            color = if (profit >= 0) Color(0xFF4CAF50) else Color(0xFFE53935),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    BadgedBox(
                        badge = {
                            if (notificationCount > 0) {
                                Badge(
                                    containerColor = Color(0xFFE53935),
                                    contentColor = Color.White
                                ) {
                                    Text(notificationCount.toString(), fontSize = 13.sp)
                                }
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Bildirimler",
                            tint = Color(0xFF4F8CFF),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                sectors.forEach { sector ->
                    FilterChip(
                        selected = selectedSector == sector,
                        onClick = { selectedSector = sector },
                        label = { Text(sector) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF4F8CFF),
                            selectedLabelColor = Color.White,
                            containerColor = Color.White.copy(alpha = 0.7f),
                            labelColor = Color(0xFF4F8CFF)
                        )
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(stocks) { stock ->
                    StockCardModern(stock)
                    if (stock != stocks.last()) {
                        Divider(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                            color = Color(0xFF4F8CFF).copy(alpha = 0.10f),
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { showSnackbar = true },
            containerColor = Color.Transparent,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(28.dp)
                .size(68.dp),
            elevation = FloatingActionButtonDefaults.elevation(18.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF4F8CFF),
                                Color(0xFF6DD5FA),
                                Color(0xFF00C6FB)
                            )
                        ),
                        shape = CircleShape
                    )
                    .border(BorderStroke(2.dp, Color.White), CircleShape)
                    .shadow(18.dp, CircleShape, clip = false),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Hisse Ekle",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        if (showSnackbar) {
            LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar(
                    message = "Portföyünüze yeni hisse ekleyin!",
                    withDismissAction = true
                )
                showSnackbar = false
            }
        }
    }
}

@Composable
fun StockCardModern(stock: Stock) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f),
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stock.code,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF222B45)
                    )
                    if (stock.isFavorite) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Favori",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                AssistChip(
                    onClick = {},
                    label = { Text(stock.sector) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFF4F8CFF).copy(alpha = 0.10f),
                        labelColor = Color(0xFF4F8CFF)
                    )
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = String.format("%.2f TL", stock.price),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    color = Color(0xFF222B45)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "%+.2f%%".format(stock.change),
                    color = when {
                        stock.change > 0 -> Color(0xFF4CAF50)
                        stock.change < 0 -> Color(0xFFE53935)
                        else -> Color.Gray
                    },
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(16.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF4F8CFF), Color(0xFF6DD5FA))
                            ),
                            shape = MaterialTheme.shapes.small
                        )
                ) {
                    Text(
                        text = "↗",
                        color = Color.White,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

data class Stock(val code: String, val price: Double, val change: Double, val sector: String, val isFavorite: Boolean)

@Preview(showBackground = true, name = "FAB Portfolio Modern Borsa Preview")
@Composable
fun PreviewFabWithPortfolioSummaryAndSnackbarExample() {
    ComposeExampleTheme {
        Column(Modifier.fillMaxSize()) {
            FabWithPortfolioSummaryAndSnackbarExample()
        }
    }
} 