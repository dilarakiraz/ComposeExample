import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GradientCardExample() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF42A5F5), Color(0xFF478DE0))
                    )
                )
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Text(
                text = "Gradient Card",
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GradientCardExamplePreview() {
    GradientCardExample()
} 