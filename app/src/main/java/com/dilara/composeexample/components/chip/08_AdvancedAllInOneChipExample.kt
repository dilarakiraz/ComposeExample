import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun AdvancedChip(
    label: String,
    avatar: String? = null,
    badge: Int? = null,
    removable: Boolean = false,
    loading: Boolean = false,
    gradient: Boolean = false,
    icon: Boolean = false,
    disabled: Boolean = false,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    onRemove: (() -> Unit)? = null
) {
    val bgColor by animateColorAsState(
        when {
            gradient -> Color.Transparent
            selected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
            disabled -> Color.LightGray.copy(alpha = 0.18f)
            else -> MaterialTheme.colorScheme.surface
        }
    )
    val borderColor by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primary else Color.LightGray
    )
    val shadowAlpha by animateFloatAsState(if (selected) 0.18f else 0.08f)
    var chipModifier = Modifier
        .padding(4.dp)
        .shadow(8.dp, RoundedCornerShape(24.dp), ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = shadowAlpha))
        .border(
            width = 2.dp,
            brush = if (gradient) Brush.horizontalGradient(listOf(Color(0xFF42A5F5), Color(0xFF7E57C2))) else Brush.linearGradient(listOf(borderColor, borderColor)),
            shape = RoundedCornerShape(24.dp)
        )
        .clip(RoundedCornerShape(24.dp))
        .clickable(enabled = !disabled && !loading) { onClick?.invoke() }
        .padding(horizontal = 18.dp, vertical = 12.dp)

    chipModifier = if (gradient) {
        chipModifier.background(
            brush = Brush.horizontalGradient(listOf(Color(0xFF42A5F5), Color(0xFF7E57C2))),
            shape = RoundedCornerShape(24.dp)
        )
    } else {
        chipModifier.background(
            color = bgColor,
            shape = RoundedCornerShape(24.dp)
        )
    }

    Box(
        modifier = chipModifier,
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (avatar != null) {
                val avatarColors = listOf(
                    Color(0xFF42A5F5), Color(0xFF7E57C2), Color(0xFF26A69A), Color(0xFFFF7043)
                )
                val avatarColor = avatarColors[label.hashCode().absoluteValue % avatarColors.size]
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(avatarColor)
                ) {
                    Text(
                        text = avatar,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            if (icon) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = label,
                color = when {
                    disabled -> Color.Gray
                    selected -> MaterialTheme.colorScheme.primary
                    gradient -> Color.White
                    else -> MaterialTheme.colorScheme.onSurface
                },
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
            if (badge != null) {
                Spacer(modifier = Modifier.width(8.dp))
                val badgeColor by animateColorAsState(if (selected) Color(0xFFFF7043) else Color.Red)
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(badgeColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = badge.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            if (loading) {
                Spacer(modifier = Modifier.width(8.dp))
                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
            }
            if (removable && onRemove != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Sil",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onRemove() }
                )
            }
        }
    }
}

@Composable
fun AdvancedAllInOneChipDemoScreen() {
    var chips by remember {
        mutableStateOf(
            listOf(
                Triple("Dilara", "A", 2),
                Triple("Yavuz Selim", "B", null),
                Triple("Berat", "C", 1),
                Triple("Hasan", null, null)
            )
        )
    }
    var selected by remember { mutableStateOf(setOf<String>()) }
    var loadingChip by remember { mutableStateOf<String?>(null) }
    var disabledChip by remember { mutableStateOf("Hasan") }
    var newChipCount by remember { mutableStateOf(1) }
    var pendingChip by remember { mutableStateOf<String?>(null) }

    if (pendingChip != null) {
        LaunchedEffect(pendingChip) {
            delay(800)
            loadingChip = null
            val name = pendingChip!!
            selected = if (selected.contains(name)) selected - name else selected + name
            pendingChip = null
        }
    }

    Card(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Gelişmiş Chip Örneği",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(18.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    val newName = "Yeni${newChipCount}"
                    chips = chips + Triple(newName, null, null)
                    newChipCount++
                }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text("Chip Ekle")
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            Column {
                chips.forEach { (name, avatar, badge) ->
                    val isSelected = selected.contains(name)
                    val isLoading = loadingChip == name
                    val isDisabled = name == disabledChip
                    AdvancedChip(
                        label = name,
                        avatar = avatar,
                        badge = badge,
                        removable = true,
                        loading = isLoading,
                        gradient = name == "Dilara",
                        icon = name == "Yavuz Selim",
                        disabled = isDisabled,
                        selected = isSelected,
                        onClick = {
                            if (!isDisabled && !isLoading) {
                                loadingChip = name
                                pendingChip = name
                            }
                        },
                        onRemove = {
                            chips = chips.filterNot { it.first == name }
                            selected = selected - name
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Seçili chipler: ${selected.joinToString(", ")}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAdvancedAllInOneChipDemoScreen() {
    AdvancedAllInOneChipDemoScreen()
} 