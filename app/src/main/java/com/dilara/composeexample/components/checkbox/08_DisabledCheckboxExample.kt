import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DisabledCheckboxExample() {
    Row {
        Checkbox(
            checked = false,
            onCheckedChange = null,
            enabled = false
        )
        Text(text = "Pasif Checkbox")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDisabledCheckboxExample() {
    DisabledCheckboxExample()
} 