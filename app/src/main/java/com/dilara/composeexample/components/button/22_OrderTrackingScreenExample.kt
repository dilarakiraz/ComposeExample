package com.dilara.composeexample.components.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OrderTrackingScreenExample() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedStatus by remember { mutableStateOf(1) }
    var showCancelDialog by remember { mutableStateOf(false) }
    var loadingTracking by remember { mutableStateOf(false) }
    var showDetails by remember { mutableStateOf(false) }
    var retryEnabled by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }
    var showDeliveredDialog by remember { mutableStateOf(false) }
    var orderCancelled by remember { mutableStateOf(false) }
    var trackingSuccess by remember { mutableStateOf(false) }

    val statusDescriptions = listOf(
        "Siparişiniz hazırlanıyor.",
        "Siparişiniz kargoya verildi.",
        "Siparişiniz teslim edildi!"
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Sipariş Durumu", style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                listOf("Hazırlanıyor", "Kargoda", "Teslim Edildi").forEachIndexed { i, label ->
                    val shape = when (i) {
                        0 -> RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp)
                        2 -> RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
                        else -> RoundedCornerShape(0.dp)
                    }
                    Button(
                        onClick = { selectedStatus = i },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedStatus == i) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = shape,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(label)
                    }
                }
            }
            Text(statusDescriptions[selectedStatus], color = MaterialTheme.colorScheme.primary)

            if (!orderCancelled && selectedStatus == 0) {
                Button(
                    onClick = { showCancelDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Siparişi İptal Et", color = Color.White)
                }
            }
            if (showCancelDialog) {
                AlertDialog(
                    onDismissRequest = { showCancelDialog = false },
                    title = { Text("Onay") },
                    text = { Text("Siparişi iptal etmek istediğinize emin misiniz?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showCancelDialog = false
                            orderCancelled = true
                            scope.launch { snackbarHostState.showSnackbar("Sipariş iptal edildi.") }
                        }) {
                            Text("Evet")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showCancelDialog = false }) {
                            Text("Hayır")
                        }
                    }
                )
            }
            if (orderCancelled) {
                Text("Siparişiniz iptal edildi.", color = Color.Red)
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = {
                        loadingTracking = true
                        trackingSuccess = false
                    },
                    enabled = !loadingTracking,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Kargo Takip")
                }
                if (loadingTracking) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                        LaunchedEffect(Unit) {
                            delay(1500)
                            loadingTracking = false
                            trackingSuccess = true
                            retryEnabled = false
                            scope.launch { snackbarHostState.showSnackbar("Kargo takip linki kopyalandı!") }
                        }
                    }
                }
            }

            Button(
                onClick = { showHelpDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Help, contentDescription = "Yardım")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Yardım")
            }
            if (showHelpDialog) {
                AlertDialog(
                    onDismissRequest = { showHelpDialog = false },
                    title = { Text("Yardım") },
                    text = { Text("Destek için 0850 000 00 00 numarasını arayabilirsiniz.") },
                    confirmButton = {
                        TextButton(onClick = { showHelpDialog = false }) {
                            Text("Tamam")
                        }
                    }
                )
            }

            Button(
                onClick = {
                    loadingTracking = true
                    trackingSuccess = false
                    retryEnabled = false
                },
                enabled = retryEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Yeniden Dene")
            }
            if (trackingSuccess) {
                Text("Kargo takip başarılı!", color = Color(0xFF388E3C))
            }

            Box(contentAlignment = Alignment.TopEnd) {
                Button(onClick = { showDeliveredDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Text("Teslim Edildi")
                }
                if (selectedStatus == 2) {
                    Badge(modifier = Modifier.offset(x = (-8).dp, y = 8.dp)) {
                        Text("✓")
                    }
                }
            }
            if (showDeliveredDialog) {
                AlertDialog(
                    onDismissRequest = { showDeliveredDialog = false },
                    title = { Text("Teslimat Detayı") },
                    text = { Text("Siparişiniz 2 saat önce teslim edildi.") },
                    confirmButton = {
                        TextButton(onClick = { showDeliveredDialog = false }) {
                            Text("Kapat")
                        }
                    }
                )
            }

            Button(
                onClick = { showDetails = !showDetails },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (showDetails) "Detayı Gizle" else "Sipariş Detayı")
            }
            AnimatedVisibility(
                visible = showDetails,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Sipariş No: 123456789")
                        Text("Ürün: Bluetooth Kulaklık")
                        Text("Tutar: 499 TL")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderTrackingScreenExample() {
    OrderTrackingScreenExample()
} 