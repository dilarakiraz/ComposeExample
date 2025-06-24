package com.dilara.composeexample.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AccessibleButtonExample() {
    Button(
        onClick = {},
        modifier = Modifier.semantics {
            contentDescription = "Favorilere ekle butonu"
            role = androidx.compose.ui.semantics.Role.Button
        }
    ) {
        Text("Erişilebilir Buton")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAccessibleButtonExample() {
    AccessibleButtonExample()
} 