import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProgressCardExample(progress: Float = 0.5f) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Progress Card")
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(progress = progress)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressCardExamplePreview() {
    ProgressCardExample(progress = 0.7f)
} 