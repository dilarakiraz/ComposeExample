package com.dilara.composeexample.applayout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * # Custom Modifiers - Basit Rehber
 *
 * Compose, yaygın davranışlar için birçok modifier sağlar, ancak kendi özel modifier'larınızı
 * da oluşturabilirsiniz. Bu dosya, custom modifier'ların temel kullanımını gösterir.
 *
 * ## Custom Modifier Türleri:
 * 1. **Mevcut Modifier'ları Zincirleme** - En kolay ve performanslı
 * 2. **Composable Modifier Factory** - Animasyon ve state için
 * 3. **drawWithContent** - Çizim işlemleri için
 * 4. **pointerInput** - Dokunma olayları için
 */

// ============================================================================
// 1. MEVCUT MODIFIER'LARI ZİNCİRLEME
// ============================================================================

/**
 * ## Mevcut Modifier'ları Zincirleme
 *
 * Çoğu zaman özel modifier'ları sadece mevcut modifier'ları kullanarak oluşturmak mümkündür.
 * Bu strateji mevcut modifier element'lerini kullanır ve siz kendi özel modifier factory'nizi sağlarsınız.
 */

// Basit custom modifier - mevcut modifier'ları birleştirme
fun Modifier.customCard(
    backgroundColor: Color = Color.White,
    cornerRadius: Dp = 8.dp,
    padding: Dp = 16.dp
) = this
    .padding(padding)
    .background(backgroundColor, RoundedCornerShape(cornerRadius))

// Daha karmaşık custom modifier
fun Modifier.customButton(
    backgroundColor: Color = Color.Blue,
    textColor: Color = Color.White,
    cornerRadius: Dp = 8.dp,
    padding: Dp = 16.dp
) = this
    .padding(padding)
    .background(backgroundColor, RoundedCornerShape(cornerRadius))

@Composable
fun ExistingModifiersChainingExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF0F8FF), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Mevcut Modifier'ları Zincirleme",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Custom card modifier kullanımı
        Card(
            modifier = Modifier.customCard(
                backgroundColor = Color(0xFFE3F2FD),
                cornerRadius = 12.dp,
                padding = 20.dp
            ),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Text(
                text = "Custom Card Modifier",
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Custom button modifier kullanımı
        Box(
            modifier = Modifier.customButton(
                backgroundColor = Color(0xFF4CAF50),
                textColor = Color.White,
                cornerRadius = 8.dp,
                padding = 12.dp
            )
        ) {
            Text(
                text = "Custom Button",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Avantajları:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "• Mevcut modifier'ları yeniden kullanır",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Kolay ve hızlı implementasyon",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Performanslı",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

// ============================================================================
// 2. COMPOSABLE MODIFIER FACTORY
// ============================================================================

/**
 * ## Composable Modifier Factory
 *
 * Composable function kullanarak özel modifier oluşturabilirsiniz. Bu yaklaşım,
 * animate*AsState ve diğer Compose state backed animation API'larını kullanmanıza olanak tanır.
 */

// Animasyonlu fade modifier
@Composable
fun Modifier.fade(enable: Boolean): Modifier {
    val alpha by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (enable) 0.5f else 1.0f,
        label = "fade"
    )
    return this.then(Modifier.drawWithContent {
        drawContent()
    })
}

// CompositionLocal kullanan modifier
@Composable
fun Modifier.fadedBackground(): Modifier {
    val color = androidx.compose.material3.LocalContentColor.current
    return this.then(Modifier.background(color.copy(alpha = 0.5f)))
}

@Composable
fun ComposableModifierFactoryExample() {
    var isFaded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFE8F5E8), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Composable Modifier Factory",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Fade modifier örneği
        Box(
            modifier = Modifier
                .size(100.dp)
                .fade(isFaded)
                .background(Color.Blue, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Fade Effect",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Fade toggle button
        Box(
            modifier = Modifier
                .clickable { isFaded = !isFaded }
                .background(Color.Gray, RoundedCornerShape(4.dp))
                .padding(8.dp)
        ) {
            Text(
                text = if (isFaded) "Show" else "Hide",
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Faded background örneği
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .fadedBackground()
        ) {
            Text(
                text = "Faded Background",
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "⚠️ Dikkat Edilmesi Gerekenler:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Red
        )

        Text(
            text = "• CompositionLocal değerleri call site'da çözülür",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Composable function modifier'lar hiç skip edilmez",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Composable function içinde çağrılmalıdır",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

// ============================================================================
// 3. DRAWWITHCONTENT MODIFIER
// ============================================================================

/**
 * ## drawWithContent Modifier
 *
 * drawWithContent kullanarak custom çizim işlemleri yapabiliriz.
 * Bu yaklaşım basit ve anlaşılırdır.
 */

// Basit circle modifier - drawWithContent kullanarak
fun Modifier.simpleCircle(color: Color) = this.drawWithContent {
    drawCircle(color)
    drawContent()
}

@Composable
fun DrawWithContentExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFFFF0E6), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "drawWithContent Modifier",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Simple circle modifier örneği
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .simpleCircle(Color.Red)
            ) {
                Text(
                    text = "Simple\nCircle",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "drawWithContent Avantajları:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "• Basit ve anlaşılır",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Modifier.Node API'sına gerek yok",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Hızlı implementasyon",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

// ============================================================================
// 4. POINTER INPUT MODIFIER
// ============================================================================

/**
 * ## Pointer Input Modifier
 *
 * pointerInput modifier'ını kullanarak custom dokunma olayları işleyebiliriz.
 * Bu yaklaşım detectTapGestures gibi gesture detection API'larını kullanmamıza olanak tanır.
 */

// Custom clickable modifier
fun Modifier.customClickable(onClick: () -> Unit) = this.pointerInput(Unit) {
    detectTapGestures { onClick() }
}

@Composable
fun PointerInputModifierExample() {
    var clickCount by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFE8F5E8), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Pointer Input Modifier",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Custom clickable örneği
        Box(
            modifier = Modifier
                .size(120.dp)
                .customClickable { clickCount++ }
                .background(Color.Blue, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Custom\nClickable\nTıklama: $clickCount",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Pointer Input Modifier özellikleri:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "• pointerInput kullanır",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• detectTapGestures kullanabilir",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Dokunma olaylarını işler",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

// ============================================================================
// 5. PERFORMANCE İPUÇLARI
// ============================================================================

/**
 * ## Custom Modifier Performance İpuçları
 *
 * Custom modifier'lar oluştururken performansı göz önünde bulundurmanız önemlidir.
 */

@Composable
fun CustomModifierPerformanceExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Custom Modifier Performance İpuçları",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "✅ İyi Pratikler:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Green
        )

        Text(
            text = "• Mevcut modifier'ları birleştirin",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• drawWithContent kullanın (basit çizimler için)",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Modifier'ları yeniden kullanın",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Composable modifier'ları gerektiğinde kullanın",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "❌ Kaçınılması Gerekenler:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Red
        )

        Text(
            text = "• Composable modifier factory'yi gereksiz kullanma",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Modifier chain'i kırma",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Her recomposition'da yeni modifier oluşturma",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

// ============================================================================
// 6. PREVIEW VE TEST
// ============================================================================

@Preview(showBackground = true, heightDp = 3000)
@Composable
fun CustomModifiersPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Custom Modifiers Örnekleri",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ExistingModifiersChainingExample()
            ComposableModifierFactoryExample()
            DrawWithContentExample()
            PointerInputModifierExample()
            CustomModifierPerformanceExample()
        }
    }
}

// Ayrı ayrı preview'lar
@Preview(showBackground = true)
@Composable
fun ExistingModifiersChainingExamplePreview() {
    MaterialTheme {
        ExistingModifiersChainingExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ComposableModifierFactoryExamplePreview() {
    MaterialTheme {
        ComposableModifierFactoryExample()
    }
}

@Preview(showBackground = true)
@Composable
fun DrawWithContentExamplePreview() {
    MaterialTheme {
        DrawWithContentExample()
    }
}

@Preview(showBackground = true)
@Composable
fun PointerInputModifierExamplePreview() {
    MaterialTheme {
        PointerInputModifierExample()
    }
}

@Preview(showBackground = true)
@Composable
fun CustomModifierPerformanceExamplePreview() {
    MaterialTheme {
        CustomModifierPerformanceExample()
    }
}

/**
 * ## Özet ve Önemli Noktalar
 *
 * ### Custom Modifier Türleri:
 * - **Mevcut Modifier'ları Zincirleme:** En kolay ve performanslı
 * - **Composable Modifier Factory:** Animasyon ve state için
 * - **drawWithContent:** Çizim işlemleri için
 * - **pointerInput:** Dokunma olayları için
 *
 * ### Temel Yaklaşımlar:
 * - **Basit:** Mevcut modifier'ları birleştirin
 * - **Orta:** drawWithContent ve pointerInput kullanın
 * - **Gelişmiş:** Composable modifier factory (dikkatli kullanın)
 *
 * ### Performance İpuçları:
 * - Mevcut modifier'ları birleştirin
 * - drawWithContent kullanın (basit çizimler için)
 * - Modifier'ları yeniden kullanın
 * - Composable modifier'ları gerektiğinde kullanın
 *
 * ### Best Practices:
 * - Modifier chain'i kırmayın
 * - Her recomposition'da yeni modifier oluşturmayın
 * - Composable modifier factory'yi gereksiz kullanmayın
 * - Performance'ı göz önünde bulundurun
 */