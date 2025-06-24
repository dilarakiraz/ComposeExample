package com.dilara.composeexample.components.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AnimatedButtonExample() {
    var selected by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (selected) Color.Red else MaterialTheme.colorScheme.primary,
        label = "buttonColor"
    )
    Button(
        onClick = { selected = !selected },
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier
    ) {
        Text(if (selected) "Kırmızı" else "Mavi")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimatedButtonExample() {
    AnimatedButtonExample()
} 