import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingDialogExample() {
    var open by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            open = true
            loading = true
        }) {
            Text("Yükleme Dialogu Aç")
        }
    }
    if (open) {
        Dialog(onDismissRequest = { open = false; loading = false }) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Button(onClick = { open = false; loading = false }, modifier = Modifier.padding(top = 16.dp)) {
                        Text("İptal")
                    }
                }
            }
        }
    }
    if (!loading && !open) {
        Text("Yükleme iptal edildi veya tamamlandı.")
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingDialogExamplePreview() {
    LoadingDialogExample()
} 