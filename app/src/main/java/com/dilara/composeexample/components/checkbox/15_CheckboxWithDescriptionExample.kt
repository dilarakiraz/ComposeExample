import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckboxWithDescriptionExample() {
    var checked by remember { mutableStateOf(false) }
    Column {
        Row {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it }
            )
            Text("Haber bültenine abone ol")
        }
        Text(
            text = "Abone olursanız haftalık güncellemeler alırsınız.",
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckboxWithDescriptionExample() {
    CheckboxWithDescriptionExample()
} 