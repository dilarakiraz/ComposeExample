import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomActionDialogExample() {
    var open by remember { mutableStateOf(false) }
    var action by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { open = true }) {
            Text("Aksiyon Dialogu Aç")
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
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(24.dp))
                        .padding(32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Dosya İşlemleri",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF1976D2)
                    )
                    Text(
                        text = "Bir aksiyon seçin:",
                        color = Color.Gray,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { action = "Paylaşıldı"; open = false },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Filled.Share, contentDescription = "Paylaş")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Paylaş")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { action = "Kopyalandı"; open = false },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Filled.ContentCopy, contentDescription = "Kopyala")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Kopyala")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { action = "Silindi"; open = false },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Filled.Delete, contentDescription = "Sil")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Sil")
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                Button(onClick = { open = false }, shape = RoundedCornerShape(16.dp), modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)) {
                    Text("İptal", fontWeight = FontWeight.SemiBold)
                }
            }
        )
    }
    if (action.isNotEmpty()) {
        Text("Sonuç: $action", color = Color.Gray, modifier = Modifier
            .width(200.dp)
            .padding(top = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CustomActionDialogExamplePreview() {
    CustomActionDialogExample()
} 