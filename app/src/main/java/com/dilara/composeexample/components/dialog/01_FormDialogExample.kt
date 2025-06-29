import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FormDialogExample() {
    var open by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { open = true }) {
            Text("Form Dialog Aç")
        }
    }
    if (open) {
        AlertDialog(
            onDismissRequest = { open = false },
            title = { Text("Kullanıcı Bilgisi") },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("İsim") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("E-posta") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (error.isNotEmpty()) {
                        Text(error, color = androidx.compose.ui.graphics.Color.Red, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    error = ""
                    if (name.isBlank() || email.isBlank()) {
                        error = "Tüm alanları doldurun."
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        error = "Geçerli bir e-posta girin."
                    } else {
                        submitted = true
                        open = false
                    }
                }) {
                    Text("Gönder")
                }
            },
            dismissButton = {
                Button(onClick = { open = false }) {
                    Text("İptal")
                }
            }
        )
    }
    if (submitted) {
        Text("Gönderildi: $name, $email", modifier = Modifier.padding(top = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun FormDialogExamplePreview() {
    FormDialogExample()
} 