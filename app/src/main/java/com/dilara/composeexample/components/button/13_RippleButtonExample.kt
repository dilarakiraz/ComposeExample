package com.dilara.composeexample.components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RippleButtonExample() {
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(8.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(color = Color.Magenta),
                onClick = { /* action */ }
            )
    ) {
        Text(
            "Ripple Renkli",
            color = Color.White,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRippleButtonExample() {
    RippleButtonExample()
} 