package com.dilara.composeexample.components.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoadingButtonExample() {
    var loading by remember { mutableStateOf(false) }
    Button(
        onClick = {
            loading = true
        },
        enabled = !loading,
        modifier = Modifier
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text("Yükleniyor...")
            LaunchedEffect(Unit) {
                delay(1500)
                loading = false
            }
        } else {
            Text("Yükle")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoadingButtonExample() {
    LoadingButtonExample()
} 