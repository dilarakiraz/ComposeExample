import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BasicCheckboxExample() {
    var checked by remember { mutableStateOf(false) }
    Row {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
        Text(text = if (checked) "Seçili" else "Seçili Değil")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBasicCheckboxExample() {
    BasicCheckboxExample()
} 