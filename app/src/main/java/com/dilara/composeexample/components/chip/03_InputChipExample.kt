import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InputChipExample() {
    var visible by remember { mutableStateOf(true) }
    if (visible) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .shadow(4.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "A",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Ali Veli", color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Kapat",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { visible = false }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInputChipExample() {
    InputChipExample()
} 