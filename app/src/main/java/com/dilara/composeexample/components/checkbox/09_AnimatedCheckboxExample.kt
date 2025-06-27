import androidx.compose.animation.animateColorAsState
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
fun AnimatedCheckboxExample() {
    var checked by remember { mutableStateOf(false) }
    val color by animateColorAsState(if (checked) Color.Green else Color.Red)
    Row {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = androidx.compose.material3.CheckboxDefaults.colors(
                checkedColor = color
            )
        )
        Text(text = if (checked) "Aktif" else "Pasif")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimatedCheckboxExample() {
    AnimatedCheckboxExample()
} 