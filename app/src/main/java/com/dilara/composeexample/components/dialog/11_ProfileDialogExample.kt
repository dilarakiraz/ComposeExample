import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dilara.composeexample.R

@Composable
fun ProfileDialogExample() {
    var open by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { open = true }) {
            Text("Profil Dialogu Aç")
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
                                colors = listOf(Color(0xFFFFF59D), Color(0xFFFBC02D))
                            )
                        )
                        .padding(32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_placeholder),
                        contentDescription = "Profil",
                        modifier = Modifier
                            .clip(CircleShape)
                            .height(80.dp)
                    )
                    Text(
                        text = "Dilara Kiraz",
                        color = Color(0xFF795548),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    Text(
                        text = "dilara@example.com",
                        color = Color(0xFF795548).copy(alpha = 0.8f),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { /* Düzenle */ }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                            Text("Düzenle")
                        }
                        Spacer(modifier = Modifier.weight(0.1f))
                        Button(onClick = { /* Çıkış */ }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                            Text("Çıkış Yap")
                        }
                    }
                    Button(onClick = { open = false }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp), shape = RoundedCornerShape(16.dp)) {
                        Text("Kapat")
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
fun ProfileDialogExamplePreview() {
    ProfileDialogExample()
} 