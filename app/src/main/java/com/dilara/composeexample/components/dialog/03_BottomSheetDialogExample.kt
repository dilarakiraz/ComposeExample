import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialogExample() {
    var open by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { open = true }) {
            Text("Bottom Sheet Aç")
        }
    }
    if (open) {
        ModalBottomSheet(onDismissRequest = { open = false }) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Bu bir bottom sheet dialog örneğidir.", modifier = Modifier.padding(bottom = 8.dp))
                Button(onClick = { open = false }, modifier = Modifier.fillMaxWidth()) {
                    Text("Kapat")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetDialogExamplePreview() {
    BottomSheetDialogExample()
} 