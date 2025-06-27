import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckboxListExample() {
    val items = listOf("Elma", "Armut", "Muz")
    val checkedStates = remember { mutableStateListOf(false, false, false) }
    Column {
        items.forEachIndexed { index, item ->
            Row {
                Checkbox(
                    checked = checkedStates[index],
                    onCheckedChange = { checkedStates[index] = it }
                )
                Text(text = item)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckboxListExample() {
    CheckboxListExample()
} 