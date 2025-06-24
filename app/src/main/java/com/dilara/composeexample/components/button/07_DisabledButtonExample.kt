package com.dilara.composeexample.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DisabledButtonExample() {
    Button(
        onClick = {},
        enabled = false,
        modifier = Modifier
    ) {
        Text("Devre Dışı")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDisabledButtonExample() {
    DisabledButtonExample()
} 