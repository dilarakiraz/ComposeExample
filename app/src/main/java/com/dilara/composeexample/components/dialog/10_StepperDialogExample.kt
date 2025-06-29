import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StepperDialogExample() {
    var open by remember { mutableStateOf(false) }
    var step by remember { mutableStateOf(0) }
    val steps = listOf("Kişisel Bilgi", "Adres", "Onay")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            open = true
            step = 0
        }) {
            Text("Stepper Dialog Aç")
        }
    }
    AnimatedVisibility(visible = open, enter = fadeIn(), exit = fadeOut()) {
        AlertDialog(
            onDismissRequest = { open = false },
            icon = null,
            title = null,
            text = {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFFE1BEE7), Color(0xFF7B1FA2))
                            )
                        )
                        .padding(32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        steps.forEachIndexed { index, label ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                val color = if (index <= step) Color(0xFF7B1FA2) else Color.LightGray
                                Text("${index + 1}", color = color, fontWeight = FontWeight.Bold)
                                Text(label, color = color, fontSize = 13.sp, modifier = Modifier.padding(top = 2.dp))
                            }
                        }
                    }
                    Text(
                        when (step) {
                            0 -> "Ad: ______\nSoyad: ______"
                            1 -> "Adres: ______"
                            2 -> "Bilgiler onaylanacak."
                            else -> ""
                        },
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
                    )
                    Row {
                        if (step > 0) {
                            Button(onClick = { step-- }, modifier = Modifier.padding(end = 8.dp), shape = RoundedCornerShape(16.dp)) {
                                Text("Geri")
                            }
                        }
                        Button(onClick = {
                            if (step < steps.size - 1) step++ else open = false
                        }, shape = RoundedCornerShape(16.dp)) {
                            Text(if (step < steps.size - 1) "İleri" else "Bitir")
                        }
                    }
                    Button(onClick = { open = false }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp), shape = RoundedCornerShape(16.dp)) {
                        Text("İptal")
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StepperDialogExamplePreview() {
    StepperDialogExample()
} 