import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BasicChipExample() {
    var selected by remember { mutableStateOf(false) }
    AssistChip(
        onClick = { selected = !selected },
        label = { Text(if (selected) "Seçili Chip" else "Chip") }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewBasicChipExample() {
    BasicChipExample()
} 