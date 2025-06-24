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
fun LongPressButtonExample() {
    var pressed by remember { mutableStateOf(false) }
    Button(
        onClick = {},
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onLongPress = { pressed = true },
                onPress = {
                    pressed = false
                    awaitRelease()
                }
            )
        }
    ) {
        Text(if (pressed) "Uzun Basıldı!" else "Uzun Basmayı Dene")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLongPressButtonExample() {
    LongPressButtonExample()
} 