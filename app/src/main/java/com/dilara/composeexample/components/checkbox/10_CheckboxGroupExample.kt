import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckboxGroupExample() {
    val items = listOf("A", "B", "C")
    val checkedStates = remember { mutableStateListOf(false, false, false) }
    val allChecked = checkedStates.all { it }
    Column {
        Button(onClick = {
            val newValue = !allChecked
            for (i in checkedStates.indices) checkedStates[i] = newValue
        }) {
            Text(if (allChecked) "Tümünü Kaldır" else "Tümünü Seç")
        }
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
fun PreviewCheckboxGroupExample() {
    CheckboxGroupExample()
} 