import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun ValidationCheckboxExample() {
    var checked by remember { mutableStateOf(false) }
    var submitted by remember { mutableStateOf(false) }
    Column {
        Row {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it }
            )
            Text(text = "Kuralları kabul ediyorum")
        }
        Button(onClick = { submitted = true }, enabled = checked) {
            Text("Onayla")
        }
        if (submitted && !checked) {
            Text("Lütfen kutucuğu işaretleyin", color = androidx.compose.ui.graphics.Color.Red)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewValidationCheckboxExample() {
    ValidationCheckboxExample()
} 