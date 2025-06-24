package com.dilara.composeexample.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Composable
fun CountdownButtonExample() {
    var enabled by remember { mutableStateOf(true) }
    var seconds by remember { mutableStateOf(10) }
    Button(
        onClick = {
            enabled = false
            seconds = 10
        },
        enabled = enabled,
        modifier = Modifier
    ) {
        Text(if (enabled) "Geri Sayım Başlat" else "$seconds sn")
    }
    if (!enabled) {
        LaunchedEffect(Unit) {
            while (seconds > 0) {
                delay(1000)
                seconds--
            }
            enabled = true
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCountdownButtonExample() {
    CountdownButtonExample()
} 