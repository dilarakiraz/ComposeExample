import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListCardExample() {
    val items = listOf("Öğe 1", "Öğe 2", "Öğe 3")
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Liste Kartı")
            Spacer(modifier = Modifier.height(8.dp))
            items.forEach { item ->
                Text(text = item, modifier = Modifier.padding(vertical = 2.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListCardExamplePreview() {
    ListCardExample()
} 