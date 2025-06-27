import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun IconTextCheckboxExample() {
    var checked by remember { mutableStateOf(false) }
    Row {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null
        )
        Text(text = if (checked) "Favori" else "Favori Değil")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIconTextCheckboxExample() {
    IconTextCheckboxExample()
} 