import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RowClickableCheckboxExample() {
    var checked by remember { mutableStateOf(false) }
    Row(modifier = Modifier.clickable { checked = !checked }) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
        Text(text = if (checked) "Aktif" else "Pasif")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRowClickableCheckboxExample() {
    RowClickableCheckboxExample()
} 