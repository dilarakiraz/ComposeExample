package com.dilara.composeexample.components.button

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ConfirmationDialogButtonExample() {
    var showDialog by remember { mutableStateOf(false) }
    Button(onClick = { showDialog = true }, modifier = Modifier) {
        Text("Sil")
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Onay") },
            text = { Text("Silmek istediğinize emin misiniz?") },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Evet")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Hayır")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConfirmationDialogButtonExample() {
    ConfirmationDialogButtonExample()
} 