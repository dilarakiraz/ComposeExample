import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LazyColumnCheckboxListExample() {
    val items = List(30) { "Öğe ${it + 1}" }
    val checkedStates = remember { mutableStateListOf(*Array(30) { false }) }
    LazyColumn {
        itemsIndexed(items) { index, item ->
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
fun PreviewLazyColumnCheckboxListExample() {
    LazyColumnCheckboxListExample()
} 