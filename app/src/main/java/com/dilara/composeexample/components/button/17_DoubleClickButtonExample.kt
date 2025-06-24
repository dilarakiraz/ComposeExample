package com.dilara.composeexample.components.button

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DoubleClickButtonExample() {
    var text by remember { mutableStateOf("Tıkla veya Çift Tıkla") }
    Button(
        onClick = { text = "Tek Tıklandı" },
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = { text = "Çift Tıklandı!" }
            )
        }
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDoubleClickButtonExample() {
    DoubleClickButtonExample()
} 