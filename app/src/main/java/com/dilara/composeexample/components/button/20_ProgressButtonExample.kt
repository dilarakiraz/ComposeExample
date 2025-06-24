package com.dilara.composeexample.components.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
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
fun ProgressButtonExample() {
    var loading by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    Column {
        Button(
            onClick = {
                loading = true
                progress = 0f
            },
            enabled = !loading,
            modifier = Modifier
        ) {
            Text(if (loading) "Yükleniyor..." else "İlerle")
        }
        if (loading) {
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(progress = progress)
            LaunchedEffect(Unit) {
                while (progress < 1f) {
                    delay(200)
                    progress += 0.1f
                }
                loading = false
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProgressButtonExample() {
    ProgressButtonExample()
} 