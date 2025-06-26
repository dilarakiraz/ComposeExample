import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectableCardExample() {
    var selected by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(if (selected) Color(0xFFB2FF59) else Color.White)
            .clickable { selected = !selected },
        border = if (selected) BorderStroke(2.dp, Color(0xFF388E3C)) else null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(if (selected) "Seçili" else "Seçili Değil")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectableCardExamplePreview() {
    SelectableCardExample()
} 