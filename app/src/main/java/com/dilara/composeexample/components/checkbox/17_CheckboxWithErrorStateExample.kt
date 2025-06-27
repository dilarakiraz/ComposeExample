import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckboxWithErrorStateExample() {
    var checked by remember { mutableStateOf(false) }
    var submitted by remember { mutableStateOf(false) }
    val isError = submitted && !checked
    Column {
        Row {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = if (isError) Color.Red else Color.Gray
                )
            )
            Text("Kabul ediyorum")
        }
        Button(onClick = { submitted = true }) {
            Text("Gönder")
        }
        if (isError) {
            Text("Lütfen kutucuğu işaretleyin", color = Color.Red)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckboxWithErrorStateExample() {
    CheckboxWithErrorStateExample()
} 