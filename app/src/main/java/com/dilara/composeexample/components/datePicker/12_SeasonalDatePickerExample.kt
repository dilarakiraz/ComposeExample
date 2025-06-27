import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

enum class Season(val title: String, val emoji: String, val icon: @Composable () -> Unit, val colors: List<Color>, val description: String) {
    SPRING(
        "İlkbahar", "🌸", { Icon(Icons.Default.LocalFlorist, null, tint = Color.White) },
        listOf(Color(0xFFE8F5E8), Color(0xFFC8E6C9), Color(0xFFA5D6A7)), "Çiçekler açıyor, doğa uyanıyor"
    ),
    SUMMER(
        "Yaz", "☀️", { Icon(Icons.Default.WbSunny, null, tint = Color.White) },
        listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2), Color(0xFFFFCC80)), "Güneşli günler, sıcak havalar"
    ),
    AUTUMN(
        "Sonbahar", "🍂", { Icon(Icons.Default.Cloud, null, tint = Color.White) },
        listOf(Color(0xFFFFF8E1), Color(0xFFFFECB3), Color(0xFFFFE082)), "Yapraklar düşüyor, hava serinliyor"
    ),
    WINTER(
        "Kış", "❄️", { Icon(Icons.Default.AcUnit, null, tint = Color.White) },
        listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB), Color(0xFF90CAF9)), "Kar yağıyor, hava soğuk"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeasonalDatePickerExample() {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val datePickerState = rememberDatePickerState()

    fun getSeason(date: LocalDate): Season {
        return when (date.month) {
            Month.MARCH, Month.APRIL, Month.MAY -> Season.SPRING
            Month.JUNE, Month.JULY, Month.AUGUST -> Season.SUMMER
            Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER -> Season.AUTUMN
            else -> Season.WINTER
        }
    }

    val currentSeason = selectedDate?.let { getSeason(it) } ?: Season.SPRING
    val rotationAngle by animateFloatAsState(
        targetValue = if (currentSeason == Season.SUMMER) 360f else 0f,
        animationSpec = tween(durationMillis = 2000),
        label = "rotation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 12.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(colors = currentSeason.colors)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedContent(
                        targetState = currentSeason,
                        transitionSpec = {
                            slideInVertically { height -> height } + fadeIn() togetherWith
                                    slideOutVertically { height -> -height } + fadeOut()
                        }
                    ) { season ->
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(
                                    when (season) {
                                        Season.SPRING -> Color(0xFF4CAF50)
                                        Season.SUMMER -> Color(0xFFFF9800)
                                        Season.AUTUMN -> Color(0xFFFF5722)
                                        Season.WINTER -> Color(0xFF2196F3)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier.rotate(rotationAngle),
                                contentAlignment = Alignment.Center
                            ) {
                                season.icon()
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedContent(
                        targetState = currentSeason,
                        transitionSpec = {
                            slideInVertically { height -> height } + fadeIn() togetherWith
                                    slideOutVertically { height -> -height } + fadeOut()
                        }
                    ) { season ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${season.emoji} ${season.title} Takvimi",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = when (season) {
                                    Season.SPRING -> Color(0xFF2E7D32)
                                    Season.SUMMER -> Color(0xFFE65100)
                                    Season.AUTUMN -> Color(0xFFBF360C)
                                    Season.WINTER -> Color(0xFF1565C0)
                                }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = season.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = when (season) {
                                    Season.SPRING -> Color(0xFF388E3C)
                                    Season.SUMMER -> Color(0xFFF57C00)
                                    Season.AUTUMN -> Color(0xFFD84315)
                                    Season.WINTER -> Color(0xFF1976D2)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = when (currentSeason) {
                                Season.SPRING -> Color(0xFF4CAF50)
                                Season.SUMMER -> Color(0xFFFF9800)
                                Season.AUTUMN -> Color(0xFFFF5722)
                                Season.WINTER -> Color(0xFF2196F3)
                            }
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Mevsim Tarihi Seç")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    selectedDate?.let { date ->
                        AnimatedContent(
                            targetState = getSeason(date),
                            transitionSpec = {
                                slideInVertically { height -> height } + fadeIn() togetherWith
                                        slideOutVertically { height -> -height } + fadeOut()
                            }
                        ) { season ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Brush.verticalGradient(
                                                colors = season.colors.map { it.copy(alpha = 0.8f) }
                                            )
                                        )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(20.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            season.icon()
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "${season.emoji} ${season.title}",
                                                style = MaterialTheme.typography.titleLarge,
                                                fontWeight = FontWeight.Bold,
                                                color = when (season) {
                                                    Season.SPRING -> Color(0xFF2E7D32)
                                                    Season.SUMMER -> Color(0xFFE65100)
                                                    Season.AUTUMN -> Color(0xFFBF360C)
                                                    Season.WINTER -> Color(0xFF1565C0)
                                                }
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(12.dp))

                                        Text(
                                            text = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, EEEE")),
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.Medium,
                                            color = when (season) {
                                                Season.SPRING -> Color(0xFF388E3C)
                                                Season.SUMMER -> Color(0xFFF57C00)
                                                Season.AUTUMN -> Color(0xFFD84315)
                                                Season.WINTER -> Color(0xFF1976D2)
                                            }
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(
                                            text = season.description,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = when (season) {
                                                Season.SPRING -> Color(0xFF4CAF50)
                                                Season.SUMMER -> Color(0xFFFF9800)
                                                Season.AUTUMN -> Color(0xFFFF5722)
                                                Season.WINTER -> Color(0xFF2196F3)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Mevsimler",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = when (currentSeason) {
                            Season.SPRING -> Color(0xFF2E7D32)
                            Season.SUMMER -> Color(0xFFE65100)
                            Season.AUTUMN -> Color(0xFFBF360C)
                            Season.WINTER -> Color(0xFF1565C0)
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Season.values().forEach { season ->
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (currentSeason == season)
                                            when (season) {
                                                Season.SPRING -> Color(0xFF4CAF50)
                                                Season.SUMMER -> Color(0xFFFF9800)
                                                Season.AUTUMN -> Color(0xFFFF5722)
                                                Season.WINTER -> Color(0xFF2196F3)
                                            }
                                        else
                                            Color.Gray.copy(alpha = 0.3f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = season.emoji,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDatePicker) {
        AlertDialog(
            onDismissRequest = { showDatePicker = false },
            title = {
                Text("Mevsim Tarihi Seçin")
            },
            text = {
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            selectedDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Seç")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("İptal")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SeasonalDatePickerExamplePreview() {
    SeasonalDatePickerExample()
} 