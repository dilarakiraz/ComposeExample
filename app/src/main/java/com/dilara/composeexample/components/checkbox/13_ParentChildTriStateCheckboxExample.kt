import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ParentChildTriStateCheckboxExample() {
    val childStates = remember { mutableStateListOf(false, false, false) }
    val parentState = when {
        childStates.all { it } -> ToggleableState.On
        childStates.none { it } -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }
    Column {
        Row {
            TriStateCheckbox(
                state = parentState,
                onClick = {
                    val newValue = parentState != ToggleableState.On
                    for (i in childStates.indices) childStates[i] = newValue
                }
            )
            Text("Tümünü Seç")
        }
        childStates.forEachIndexed { i, checked ->
            Row {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { childStates[i] = it }
                )
                Text("Seçenek ${i + 1}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewParentChildTriStateCheckboxExample() {
    ParentChildTriStateCheckboxExample()
} 