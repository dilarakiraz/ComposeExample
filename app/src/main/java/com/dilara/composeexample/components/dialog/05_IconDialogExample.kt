import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dilara.composeexample.R

@Composable
fun IconDialogExample() {
    var open by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { open = true }) {
            Text("İkonlu Dialog Aç")
        }
    }
    if (open) {
        AlertDialog(
            onDismissRequest = { open = false },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = "Bilgi",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            },
            title = { Text("Bilgilendirme") },
            text = {
                Column {
                    Text("Bu dialogda bir ikon var.")
                }
            },
            confirmButton = {
                Button(onClick = { open = false }) {
                    Text("Tamam")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IconDialogExamplePreview() {
    IconDialogExample()
} 