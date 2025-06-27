import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .background(
                if (selected) MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                else MaterialTheme.colorScheme.surface,
                RoundedCornerShape(24.dp)
            )
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) MaterialTheme.colorScheme.secondary else Color.LightGray,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 18.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.FilterList,
            contentDescription = null,
            tint = if (selected) MaterialTheme.colorScheme.secondary else Color.Gray,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun FilterChipDemoScreen() {
    var selected by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Filtre Paneli",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            FilterChip(
                text = if (selected) "Filtre Aktif" else "Filtre",
                selected = selected,
                onClick = { selected = !selected }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Filtre chip'e tıklayarak aktif/pasif yapabilirsiniz.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterChipDemoScreen() {
    FilterChipDemoScreen()
} 