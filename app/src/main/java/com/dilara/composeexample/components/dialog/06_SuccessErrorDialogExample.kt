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
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dilara.composeexample.R

@Composable
fun SuccessErrorDialogExample() {
    var open by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { open = true; isSuccess = true }) {
                Text("Başarı Dialogu Aç")
            }
            Button(onClick = { open = true; isSuccess = false }, modifier = Modifier.padding(top = 8.dp)) {
                Text("Hata Dialogu Aç")
            }
        }
    }
    AnimatedVisibility(visible = open, enter = fadeIn(), exit = fadeOut()) {
        AlertDialog(
            onDismissRequest = { open = false },
            icon = null,
            title = null,
            text = {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = if (isSuccess)
                                    listOf(Color(0xFFB2FF59), Color(0xFF43A047))
                                else
                                    listOf(Color(0xFFFF8A80), Color(0xFFD32F2F))
                            )
                        )
                        .padding(32.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = if (isSuccess) R.drawable.ic_check else R.drawable.ic_error),
                            contentDescription = if (isSuccess) "Başarılı" else "Hata",
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = if (isSuccess) "Başarılı!" else "Hata!",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Text(
                            text = if (isSuccess) "İşleminiz başarıyla tamamlandı." else "Bir hata oluştu. Lütfen tekrar deneyin.",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                        )
                        Button(
                            onClick = { open = false },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Tamam", fontWeight = FontWeight.SemiBold)
                        }
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
fun SuccessErrorDialogExamplePreview() {
    SuccessErrorDialogExample()
} 