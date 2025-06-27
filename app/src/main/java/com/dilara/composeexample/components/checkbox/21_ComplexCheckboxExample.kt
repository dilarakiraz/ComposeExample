import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ComplexCheckboxExample() {
    val childLabels = listOf("Bildirimler", "E-posta", "SMS")
    val checkedStates = remember { mutableStateListOf(false, false, false) }
    var loadingIndex by remember { mutableStateOf(-1) }
    var errorIndex by remember { mutableStateOf(-1) }

    val parentState = when {
        checkedStates.all { it } -> ToggleableState.On
        checkedStates.none { it } -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "İzinler Ayarları",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TriStateCheckbox(
                        state = parentState,
                        onClick = {
                            val newValue = parentState != ToggleableState.On
                            for (i in checkedStates.indices) checkedStates[i] = newValue
                        }
                    )
                    Text("Tüm İzinler", modifier = Modifier.padding(start = 8.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                childLabels.forEachIndexed { i, label ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = checkedStates[i],
                                onCheckedChange = {
                                    loadingIndex = i
                                    errorIndex = -1
                                    checkedStates[i] = it
                                },
                                enabled = loadingIndex != i
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            if (loadingIndex == i) {
                                LaunchedEffect(i, checkedStates[i]) {
                                    delay(1000)
                                    if (i == 1 && checkedStates[i]) {
                                        checkedStates[i] = false
                                        errorIndex = i
                                    }
                                    loadingIndex = -1
                                }
                                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                            } else {
                                Text(label)
                            }
                        }
                        if (errorIndex == i) {
                            Text(
                                text = "Bu izin verilemiyor!",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 36.dp, bottom = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Açıklama: 'E-posta' izni seçilemez, diğerleri seçilebilir. Seçimlerde loading ve hata durumu gösterilir.",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewComplexCheckboxExample() {
    ComplexCheckboxExample()
} 