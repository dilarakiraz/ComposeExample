import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckboxWithRippleEffectExample() {
    var checked by remember { mutableStateOf(false) }
    Row {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            interactionSource = remember { MutableInteractionSource() },
            modifier = Modifier,
        )
        Text(text = if (checked) "Ripple Etkili" else "Ripple Yok")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckboxWithRippleEffectExample() {
    CheckboxWithRippleEffectExample()
} 