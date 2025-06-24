package com.dilara.composeexample.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GradientButtonExample() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = ButtonDefaults.ContentPadding,
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF42A5F5), Color(0xFF478DE0))
                ),
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Text("Gradient Button", color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGradientButtonExample() {
    GradientButtonExample()
} 