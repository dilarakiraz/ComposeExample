import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckboxWithImageExample() {
    var checked by remember { mutableStateOf(false) }
    Row {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_camera),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(text = if (checked) "Kamera Açık" else "Kamera Kapalı")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckboxWithImageExample() {
    CheckboxWithImageExample()
} 