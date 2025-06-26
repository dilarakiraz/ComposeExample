import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedCardExample() {
    var expanded by remember { mutableStateOf(false) }
    val cardHeight by animateDpAsState(targetValue = if (expanded) 200.dp else 100.dp, label = "cardHeight")

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(cardHeight)
            .clickable { expanded = !expanded }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = if (expanded) "Büyük Card" else "Küçük Card",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedCardExamplePreview() {
    AnimatedCardExample()
} 