import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AccessibleCheckboxExample() {
    var checked by remember { mutableStateOf(false) }
    Row {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            modifier = Modifier
                .size(40.dp)
                .semantics { contentDescription = if (checked) "Seçili" else "Seçili değil" }
        )
        Text(text = if (checked) "Erişilebilir" else "Erişilebilir Değil")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAccessibleCheckboxExample() {
    AccessibleCheckboxExample()
} 