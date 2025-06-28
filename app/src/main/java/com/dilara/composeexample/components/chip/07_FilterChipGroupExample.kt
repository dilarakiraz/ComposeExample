import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FilterGridChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(
                if (selected) MaterialTheme.colorScheme.secondary else Color.Transparent,
                RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = if (selected) MaterialTheme.colorScheme.secondary else Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = text,
                color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun FilterChipGroupDemoScreen() {
    val filters = listOf("Spor", "Ekonomi", "Teknoloji", "Sağlık", "Kültür", "Sanat", "Dünya", "Gündem")
    val selectedStates = remember { mutableStateListOf(*Array(filters.size) { false }) }
    Card(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Filtreler",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            for (row in filters.chunked(2)) {
                Row {
                    row.forEachIndexed { index, label ->
                        val filterIndex = filters.indexOf(label)
                        FilterGridChip(
                            text = label,
                            selected = selectedStates[filterIndex],
                            onClick = { selectedStates[filterIndex] = !selectedStates[filterIndex] }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            val selectedLabels = filters.filterIndexed { i, _ -> selectedStates[i] }
            if (selectedLabels.isNotEmpty()) {
                Text(
                    text = "Seçili filtreler: ${selectedLabels.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /* Filtre uygula işlemi */ }) {
                Text("Filtreyi Uygula")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterChipGroupDemoScreen() {
    FilterChipGroupDemoScreen()
} 