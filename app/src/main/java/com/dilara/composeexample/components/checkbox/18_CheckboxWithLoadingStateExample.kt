import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun CheckboxWithLoadingStateExample() {
    var checked by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    Row {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                loading = true
                checked = it
            },
            enabled = !loading
        )
        Spacer(modifier = Modifier.width(8.dp))
        if (loading) {
            LaunchedEffect(Unit) {
                delay(1000)
                loading = false
            }
            CircularProgressIndicator(modifier = Modifier.width(24.dp), strokeWidth = 2.dp)
        } else {
            Text(text = if (checked) "Aktif" else "Pasif")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckboxWithLoadingStateExample() {
    CheckboxWithLoadingStateExample()
} 