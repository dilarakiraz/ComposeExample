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
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Thunderstorm
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
import kotlin.random.Random

enum class WeatherType(
    val title: String,
    val emoji: String,
    val icon: @Composable () -> Unit,
    val color: Color,
    val description: String
) {
    SUNNY(
        "Güneşli", "☀️", { Icon(Icons.Default.WbSunny, null, tint = Color.White) },
        Color(0xFFFF9800), "Açık ve güneşli hava"
    ),
    CLOUDY(
        "Bulutlu", "☁️", { Icon(Icons.Default.Cloud, null, tint = Color.White) },
        Color(0xFF9E9E9E), "Bulutlu ve kapalı hava"
    ),
    RAINY(
        "Yağmurlu", "🌧️", { Icon(Icons.Default.Opacity, null, tint = Color.White) },
        Color(0xFF2196F3), "Yağmur yağışı"
    ),
    SNOWY(
        "Karlı", "❄️", { Icon(Icons.Default.AcUnit, null, tint = Color.White) },
        Color(0xFF90CAF9), "Kar yağışı"
    ),
    STORMY(
        "Fırtınalı", "⛈️", { Icon(Icons.Default.Thunderstorm, null, tint = Color.White) },
        Color(0xFF673AB7), "Gök gürültülü fırtına"
    ),
    WINDY(
        "Rüzgarlı", "💨", { Icon(Icons.Default.Air, null, tint = Color.White) },
        Color(0xFF4CAF50), "Rüzgarlı hava"
    )
}

data class WeatherInfo(
    val type: WeatherType,
    val temperature: Int,
    val humidity: Int,
    val windSpeed: Int,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDatePickerExample() {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val datePickerState = rememberDatePickerState()

    fun getWeatherForDate(date: LocalDate): WeatherInfo {
        val month = date.month
        val random = Random(date.toEpochDay())

        val weatherType = when (month) {
            Month.DECEMBER, Month.JANUARY, Month.FEBRUARY -> {
                when (random.nextInt(100)) {
                    in 0..40 -> WeatherType.SNOWY
                    in 41..70 -> WeatherType.CLOUDY
                    in 71..85 -> WeatherType.SUNNY
                    else -> WeatherType.WINDY
                }
            }

            Month.MARCH, Month.APRIL, Month.MAY -> {
                when (random.nextInt(100)) {
                    in 0..30 -> WeatherType.RAINY
                    in 31..60 -> WeatherType.CLOUDY
                    in 61..85 -> WeatherType.SUNNY
                    else -> WeatherType.WINDY
                }
            }

            Month.JUNE, Month.JULY, Month.AUGUST -> {
                when (random.nextInt(100)) {
                    in 0..60 -> WeatherType.SUNNY
                    in 61..80 -> WeatherType.CLOUDY
                    in 81..90 -> WeatherType.RAINY
                    else -> WeatherType.STORMY
                }
            }

            else -> {
                when (random.nextInt(100)) {
                    in 0..40 -> WeatherType.CLOUDY
                    in 41..70 -> WeatherType.RAINY
                    in 71..85 -> WeatherType.SUNNY
                    else -> WeatherType.WINDY
                }
            }
        }

        val temperature = when (weatherType) {
            WeatherType.SUNNY -> random.nextInt(20, 35)
            WeatherType.CLOUDY -> random.nextInt(15, 25)
            WeatherType.RAINY -> random.nextInt(10, 20)
            WeatherType.SNOWY -> random.nextInt(-5, 5)
            WeatherType.STORMY -> random.nextInt(15, 25)
            WeatherType.WINDY -> random.nextInt(10, 25)
        }

        val humidity = when (weatherType) {
            WeatherType.SUNNY -> random.nextInt(30, 50)
            WeatherType.CLOUDY -> random.nextInt(50, 70)
            WeatherType.RAINY -> random.nextInt(80, 95)
            WeatherType.SNOWY -> random.nextInt(60, 80)
            WeatherType.STORMY -> random.nextInt(70, 90)
            WeatherType.WINDY -> random.nextInt(40, 60)
        }

        val windSpeed = when (weatherType) {
            WeatherType.SUNNY -> random.nextInt(5, 15)
            WeatherType.CLOUDY -> random.nextInt(10, 20)
            WeatherType.RAINY -> random.nextInt(15, 25)
            WeatherType.SNOWY -> random.nextInt(5, 15)
            WeatherType.STORMY -> random.nextInt(25, 40)
            WeatherType.WINDY -> random.nextInt(20, 35)
        }

        return WeatherInfo(
            type = weatherType,
            temperature = temperature,
            humidity = humidity,
            windSpeed = windSpeed,
            description = weatherType.description
        )
    }

    val selectedWeather = selectedDate?.let { getWeatherForDate(it) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (selectedWeather?.type == WeatherType.WINDY) 360f else 0f,
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
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF87CEEB),
                                Color(0xFF98D8E8),
                                Color(0xFFB0E0E6)
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "🌤️ Hava Durumu Takvimi",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Seçilen tarihin hava durumunu öğrenin",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Color(0xFF87CEEB)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Tarih Seç",
                            color = Color(0xFF87CEEB)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    selectedDate?.let { date ->
                        selectedWeather?.let { weather ->
                            AnimatedContent(
                                targetState = weather,
                                transitionSpec = {
                                    slideInVertically { height -> height } + fadeIn() togetherWith
                                            slideOutVertically { height -> -height } + fadeOut()
                                }
                            ) { currentWeather ->
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
                                                    colors = listOf(
                                                        currentWeather.type.color.copy(alpha = 0.1f),
                                                        currentWeather.type.color.copy(alpha = 0.05f)
                                                    )
                                                )
                                            )
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(20.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, EEEE")),
                                                style = MaterialTheme.typography.bodyLarge,
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )

                                            Spacer(modifier = Modifier.height(16.dp))

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(60.dp)
                                                        .clip(CircleShape)
                                                        .background(currentWeather.type.color),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Box(
                                                        modifier = Modifier.rotate(rotationAngle),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        currentWeather.type.icon()
                                                    }
                                                }

                                                Spacer(modifier = Modifier.width(16.dp))

                                                Column {
                                                    Text(
                                                        text = "${currentWeather.temperature}°C",
                                                        style = MaterialTheme.typography.headlineLarge,
                                                        fontWeight = FontWeight.Bold,
                                                        color = currentWeather.type.color
                                                    )
                                                    Text(
                                                        text = currentWeather.type.title,
                                                        style = MaterialTheme.typography.titleMedium,
                                                        color = MaterialTheme.colorScheme.onSurface
                                                    )
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(16.dp))

                                            Text(
                                                text = currentWeather.description,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )

                                            Spacer(modifier = Modifier.height(20.dp))

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceEvenly
                                            ) {
                                                WeatherDetail(
                                                    icon = Icons.Default.Opacity,
                                                    value = "${currentWeather.humidity}%",
                                                    label = "Nem",
                                                    color = Color(0xFF2196F3)
                                                )
                                                WeatherDetail(
                                                    icon = Icons.Default.Air,
                                                    value = "${currentWeather.windSpeed} km/h",
                                                    label = "Rüzgar",
                                                    color = Color(0xFF4CAF50)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Hava Durumu Türleri",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WeatherType.values().take(3).forEach { weatherType ->
                            WeatherTypeChip(weatherType = weatherType)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WeatherType.values().drop(3).forEach { weatherType ->
                            WeatherTypeChip(weatherType = weatherType)
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
                Text("Hava Durumu Tarihi Seçin")
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

@Composable
fun WeatherDetail(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun WeatherTypeChip(weatherType: WeatherType) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(weatherType.color.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = weatherType.emoji,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherDatePickerExamplePreview() {
    WeatherDatePickerExample()
} 