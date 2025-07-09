package com.dilara.composeexample.components.floatingActionButton

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dilara.composeexample.ui.theme.ComposeExampleTheme

@Composable
fun FabWithExpandableTradeActionsExample() {
    var expanded by remember { mutableStateOf(false) }
    var dialogType by remember { mutableStateOf<TradeAction?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = expanded) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f))
                    .clickable { expanded = false }
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 32.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.End
        ) {
            AnimatedVisibility(visible = expanded) {
                MiniFabModern(
                    color = Color(0xFF4CAF50),
                    icon = Icons.Default.ArrowUpward,
                    label = "Al",
                    onClick = { dialogType = TradeAction.BUY; expanded = false }
                )
            }
            AnimatedVisibility(visible = expanded) {
                MiniFabModern(
                    color = Color(0xFFE53935),
                    icon = Icons.Default.ArrowDownward,
                    label = "Sat",
                    onClick = { dialogType = TradeAction.SELL; expanded = false }
                )
            }
            AnimatedVisibility(visible = expanded) {
                MiniFabModern(
                    color = Color(0xFFFFC107),
                    icon = Icons.Default.Star,
                    label = "Favori",
                    onClick = { dialogType = TradeAction.FAVORITE; expanded = false }
                )
            }
        }
        FloatingActionButton(
            onClick = { expanded = !expanded },
            containerColor = Color(0xFF4F8CFF),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp)
                .size(68.dp)
                .shadow(16.dp, shape = MaterialTheme.shapes.extraLarge)
                .alpha(if (expanded) 0.95f else 1f)
        ) {
            Icon(Icons.Default.Add, contentDescription = "İşlem", modifier = Modifier.size(36.dp))
        }
        when (dialogType) {
            TradeAction.BUY -> TradeDialog(title = "Hisse Al", onDismiss = { dialogType = null })
            TradeAction.SELL -> TradeDialog(title = "Hisse Sat", onDismiss = { dialogType = null })
            TradeAction.FAVORITE -> TradeDialog(title = "Favorilere Ekle", onDismiss = { dialogType = null })
            null -> {}
        }
    }
}

enum class TradeAction { BUY, SELL, FAVORITE }

@Composable
fun MiniFabModern(color: Color, icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            color = color,
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 8.dp
        ) {
            Text(
                text = label,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        FloatingActionButton(
            onClick = onClick,
            containerColor = color,
            contentColor = Color.White,
            modifier = Modifier.size(54.dp),
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {
            Icon(icon, contentDescription = label, modifier = Modifier.size(28.dp))
        }
    }
}

@Composable
fun TradeDialog(title: String, onDismiss: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    val amountInt = amount.toIntOrNull() ?: 0
    val priceDouble = price.toDoubleOrNull() ?: 0.0
    val total = amountInt * priceDouble
    val isBuy = title.contains("Al")
    val stockCode = "AKBNK"

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = 8.dp,
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$stockCode ${if (isBuy) "Al" else "Sat"}",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (isBuy) Color(0xFF4CAF50) else Color(0xFFE53935)
                )
                Spacer(modifier = Modifier.height(18.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { if (it.all { c -> c.isDigit() }) amount = it },
                    label = { Text("Adet") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { if (it.isEmpty() || it.matches(Regex("\\d*\\.?\\d*"))) price = it },
                    label = { Text("Fiyat (TL)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Toplam: %.2f TL".format(total),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF4F8CFF)
                )
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text("İptal")
                    }
                    Button(
                        onClick = { onDismiss() },
                        enabled = amountInt > 0 && priceDouble > 0.0,
                        colors = ButtonDefaults.buttonColors(containerColor = if (isBuy) Color(0xFF4CAF50) else Color(0xFFE53935))
                    ) {
                        Text("Onayla", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "FAB Expandable Trade Actions Modern Preview")
@Composable
fun PreviewFabWithExpandableTradeActionsExample() {
    ComposeExampleTheme {
        Box(Modifier.fillMaxSize()) {
            FabWithExpandableTradeActionsExample()
        }
    }
} 