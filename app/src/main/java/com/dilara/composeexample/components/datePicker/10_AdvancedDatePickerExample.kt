import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import java.time.format.DateTimeFormatter

enum class DatePickerMode {
    SINGLE, RANGE, MULTIPLE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedDatePickerExample() {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedMode by remember { mutableStateOf(DatePickerMode.SINGLE) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedDates by remember { mutableStateOf(listOf<LocalDate>()) }
    var showSettings by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val rotationAngle by animateFloatAsState(
        targetValue = if (showSettings) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
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
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.secondaryContainer,
                                MaterialTheme.colorScheme.tertiaryContainer
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null,
                                modifier = Modifier.size(35.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        IconButton(
                            onClick = { showSettings = !showSettings }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Ayarlar",
                                modifier = Modifier.rotate(rotationAngle),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "🎯 Gelişmiş Tarih Seçici",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Farklı seçim modları ile tarih yönetimi",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    AnimatedVisibility(
                        visible = showSettings,
                        enter = slideInVertically() + fadeIn(),
                        exit = slideOutVertically() + fadeOut()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            Text(
                                text = "Seçim Modu",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { selectedMode = DatePickerMode.SINGLE },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                                        containerColor = if (selectedMode == DatePickerMode.SINGLE)
                                            MaterialTheme.colorScheme.primaryContainer
                                        else
                                            Color.Transparent
                                    )
                                ) {
                                    Icon(Icons.Default.Today, null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Tek")
                                }

                                OutlinedButton(
                                    onClick = { selectedMode = DatePickerMode.RANGE },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                                        containerColor = if (selectedMode == DatePickerMode.RANGE)
                                            MaterialTheme.colorScheme.primaryContainer
                                        else
                                            Color.Transparent
                                    )
                                ) {
                                    Icon(Icons.Default.DateRange, null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Aralık")
                                }

                                OutlinedButton(
                                    onClick = { selectedMode = DatePickerMode.MULTIPLE },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                                        containerColor = if (selectedMode == DatePickerMode.MULTIPLE)
                                            MaterialTheme.colorScheme.primaryContainer
                                        else
                                            Color.Transparent
                                    )
                                ) {
                                    Icon(Icons.Default.CalendarToday, null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Çoklu")
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            when (selectedMode) {
                                DatePickerMode.SINGLE -> "Tek Tarih Seç"
                                DatePickerMode.RANGE -> "Tarih Aralığı Seç"
                                DatePickerMode.MULTIPLE -> "Çoklu Tarih Seç"
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Results Display
                    when (selectedMode) {
                        DatePickerMode.SINGLE -> {
                            selectedDate?.let { date ->
                                ResultCard(
                                    title = "Seçilen Tarih",
                                    content = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, EEEE")),
                                    icon = Icons.Default.Today
                                )
                            }
                        }

                        DatePickerMode.RANGE -> {
                            if (startDate != null && endDate != null) {
                                ResultCard(
                                    title = "Tarih Aralığı",
                                    content = "${startDate!!.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))} - ${endDate!!.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
                                    icon = Icons.Default.DateRange
                                )
                            }
                        }

                        DatePickerMode.MULTIPLE -> {
                            if (selectedDates.isNotEmpty()) {
                                LazyColumn(
                                    modifier = Modifier.height(200.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(selectedDates.sorted()) { date ->
                                        ResultCard(
                                            title = "Seçilen Tarih",
                                            content = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, EEEE")),
                                            icon = Icons.Default.CalendarToday
                                        )
                                    }
                                }
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
                Text(
                    when (selectedMode) {
                        DatePickerMode.SINGLE -> "Tek Tarih Seçin"
                        DatePickerMode.RANGE -> "Tarih Aralığı Seçin"
                        DatePickerMode.MULTIPLE -> "Tarih Ekle"
                    }
                )
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
                            val date = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                            when (selectedMode) {
                                DatePickerMode.SINGLE -> {
                                    selectedDate = date
                                }

                                DatePickerMode.RANGE -> {
                                    if (startDate == null) {
                                        startDate = date
                                    } else if (endDate == null) {
                                        if (date.isAfter(startDate)) {
                                            endDate = date
                                        } else {
                                            endDate = startDate
                                            startDate = date
                                        }
                                    } else {
                                        startDate = date
                                        endDate = null
                                    }
                                }

                                DatePickerMode.MULTIPLE -> {
                                    if (!selectedDates.contains(date)) {
                                        selectedDates = selectedDates + date
                                    }
                                }
                            }
                        }
                        if (selectedMode != DatePickerMode.RANGE || endDate != null) {
                            showDatePicker = false
                        }
                    }
                ) {
                    Text(
                        when (selectedMode) {
                            DatePickerMode.SINGLE -> "Seç"
                            DatePickerMode.RANGE -> if (startDate == null) "Başlangıç" else "Bitiş"
                            DatePickerMode.MULTIPLE -> "Ekle"
                        }
                    )
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
fun ResultCard(
    title: String,
    content: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdvancedDatePickerExamplePreview() {
    AdvancedDatePickerExample()
} 