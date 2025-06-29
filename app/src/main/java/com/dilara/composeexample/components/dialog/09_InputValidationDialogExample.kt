import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
fun InputValidationDialogExample() {
    var open by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var success by remember { mutableStateOf(false) }

    fun validatePassword(pw: String): String {
        return when {
            pw.length < 6 -> "Şifre en az 6 karakter olmalı."
            !pw.any { it.isDigit() } -> "Şifre en az bir rakam içermeli."
            !pw.any { it.isUpperCase() } -> "Şifre en az bir büyük harf içermeli."
            else -> ""
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { open = true }) {
            Text("Şifre Dialogu Aç")
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
                                colors = listOf(Color(0xFFB2EBF2), Color(0xFF0097A7))
                            )
                        )
                        .padding(32.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Şifre Oluştur",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            error = validatePassword(it)
                        },
                        label = { Text("Şifre") },
                        isError = error.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        shape = RoundedCornerShape(12.dp)
                    )
                    if (error.isNotEmpty()) {
                        Text(error, color = Color(0xFFFFCDD2), modifier = Modifier.padding(top = 4.dp))
                    } else if (password.isNotEmpty()) {
                        Text("Şifre güçlü!", color = Color(0xFFB2FF59), modifier = Modifier.padding(top = 4.dp))
                    }
                    Button(
                        onClick = {
                            if (error.isEmpty() && password.isNotEmpty()) {
                                success = true
                                open = false
                            }
                        },
                        enabled = error.isEmpty() && password.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Kaydet", fontWeight = FontWeight.SemiBold)
                    }
                    Button(
                        onClick = { open = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("İptal", fontWeight = FontWeight.SemiBold)
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }
    if (success) {
        Text("Şifre başarıyla kaydedildi!", color = Color(0xFF388E3C), modifier = Modifier.padding(top = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun InputValidationDialogExamplePreview() {
    InputValidationDialogExample()
} 