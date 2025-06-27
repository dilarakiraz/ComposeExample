import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun IndeterminateCheckboxExample() {
    var state by remember { mutableStateOf(ToggleableState.Indeterminate) }
    Row {
        TriStateCheckbox(
            state = state,
            onClick = {
                state = when (state) {
                    ToggleableState.On -> ToggleableState.Off
                    ToggleableState.Off -> ToggleableState.Indeterminate
                    ToggleableState.Indeterminate -> ToggleableState.On
                }
            }
        )
        Text(
            text = when (state) {
                ToggleableState.On -> "Açık"
                ToggleableState.Off -> "Kapalı"
                ToggleableState.Indeterminate -> "Belirsiz"
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIndeterminateCheckboxExample() {
    IndeterminateCheckboxExample()
} 