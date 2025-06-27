import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row

@Composable
fun CustomColorCheckboxExample() {
    var checked by remember { mutableStateOf(false) }
    Row {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Magenta,
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.Yellow
            )
        )
        Text(text = "Renkli Checkbox")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomColorCheckboxExample() {
    CustomColorCheckboxExample()
} 