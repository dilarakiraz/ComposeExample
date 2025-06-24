package com.dilara.composeexample.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BadgeButtonExample() {
    Box(contentAlignment = Alignment.TopEnd) {
        Button(onClick = {}) {
            Text("Bildirimli")
        }
        Badge(modifier = Modifier.offset(x = (-8).dp, y = 8.dp)) {
            Text("3")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBadgeButtonExample() {
    BadgeButtonExample()
} 