import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SuggestionChipExample() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF42A5F5), Color(0xFF7E57C2))
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Öneri Chip",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuggestionChipExample() {
    SuggestionChipExample()
} 