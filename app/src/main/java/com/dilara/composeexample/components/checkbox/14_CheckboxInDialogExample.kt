import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckboxInDialogExample() {
    var showDialog by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    Button(onClick = { showDialog = true }) {
        Text("Dialog Aç")
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Onay") },
            text = {
                Row {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it }
                    )
                    Text("Beni bir daha gösterme")
                }
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Tamam")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckboxInDialogExample() {
    CheckboxInDialogExample()
} 