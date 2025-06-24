package com.dilara.composeexample.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ElevationButtonExample() {
    Button(
        onClick = {},
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 16.dp,
            pressedElevation = 24.dp,
            focusedElevation = 20.dp
        ),
        modifier = Modifier
    ) {
        Text("Yüksek Gölge")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewElevationButtonExample() {
    ElevationButtonExample()
} 