import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MultiStepDialogExample() {
    var open by remember { mutableStateOf(false) }
    var step by remember { mutableStateOf(1) }
    var completed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            open = true
            step = 1
            completed = false
        }) {
            Text("Çok Adımlı Dialog Aç")
        }
    }
    if (open) {
        AlertDialog(
            onDismissRequest = { open = false },
            title = { Text("Adım $step") },
            text = {
                Column {
                    when (step) {
                        1 -> Text("Bu birinci adım.")
                        2 -> Text("Bu ikinci adım.")
                        3 -> Text("Bu üçüncü adım.")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (step < 3) step++ else {
                        completed = true; open = false
                    }
                }) {
                    Text(if (step < 3) "İleri" else "Bitir")
                }
            },
            dismissButton = {
                Button(onClick = { open = false }) {
                    Text("İptal")
                }
            }
        )
    }
    if (completed) {
        Text("Tüm adımlar tamamlandı!")
    }
}

@Preview(showBackground = true)
@Composable
fun MultiStepDialogExamplePreview() {
    MultiStepDialogExample()
} 