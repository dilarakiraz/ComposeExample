package com.dilara.composeexample.components.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IconButtonExample() {
    Button(
        onClick = {},
        modifier = Modifier
    ) {
        Icon(Icons.Default.Favorite, contentDescription = "Favori", modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.size(8.dp))
        Text("Favori")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIconButtonExample() {
    IconButtonExample()
} 