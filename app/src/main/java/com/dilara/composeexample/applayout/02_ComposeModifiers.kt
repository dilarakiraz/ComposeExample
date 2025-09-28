package com.dilara.composeexample.applayout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * # Compose Modifiers - Kapsamlı Rehber
 *
 * Modifier'lar, composable'larınızı dekore etmenize veya geliştirmenize olanak tanır.
 * Modifier'lar şu tür işlemler yapmanızı sağlar:
 *
 * - Composable'ın boyutunu, layout'unu, davranışını ve görünümünü değiştirme
 * - Erişilebilirlik etiketleri gibi bilgiler ekleme
 * - Kullanıcı girişini işleme
 * - Element'i tıklanabilir, kaydırılabilir, sürüklenebilir veya yakınlaştırılabilir
 *   yapma gibi üst düzey etkileşimler ekleme
 *
 * Modifier'lar standart Kotlin nesneleridir. Modifier sınıfı fonksiyonlarından
 * birini çağırarak bir modifier oluşturun.
 */

// ============================================================================
// 1. TEMEL MODIFIER KULLANIMI
// ============================================================================

/**
 * ## Temel Modifier Örneği
 *
 * En basit modifier kullanımı - padding ekleme
 */

@Composable
fun BasicModifierExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(Color(0xFFADD8E6), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Temel Modifier Örneği",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = "Merhaba,")
        Text(text = "Dünya!")
    }
}

/**
 * ## Modifier Zincirleme (Chaining)
 *
 * Bu fonksiyonları birlikte zincirleyerek birleştirebilirsiniz.
 * Modifier'ların sırası önemlidir!
 */

@Composable
fun ModifierChainingExample() {
    Column(
        modifier = Modifier
            .padding(24.dp)        // 1. Önce padding ekle
            .fillMaxWidth()        // 2. Sonra genişliği doldur
            .background(Color(0xFF90EE90), RoundedCornerShape(8.dp)) // 3. Arka plan ekle
            .padding(16.dp)        // 4. İç padding ekle
    ) {
        Text(
            text = "Modifier Zincirleme",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text("padding -> fillMaxWidth -> background -> padding")
    }
}

// ============================================================================
// 2. MODIFIER SIRASI ÖNEMİ
// ============================================================================

/**
 * ## Modifier Sırasının Önemi
 *
 * Modifier fonksiyonlarının sırası önemlidir. Her fonksiyon, önceki fonksiyonun
 * döndürdüğü Modifier'da değişiklik yapar, bu nedenle sıra sonucu etkiler.
 */

@Composable
fun ModifierOrderExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFFFE4E1), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Modifier Sırası Önemli",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Örnek 1: clickable -> padding (tüm alan tıklanabilir)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Tıklama işlemi */ }
                .padding(8.dp)
                .background(Color.White, RoundedCornerShape(4.dp))
                .padding(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Text("Tüm alan tıklanabilir (padding dahil)")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Örnek 2: padding -> clickable (sadece içerik tıklanabilir)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { /* Tıklama işlemi */ }
                .background(Color.White, RoundedCornerShape(4.dp))
                .padding(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Text("Sadece içerik tıklanabilir")
        }
    }
}

// ============================================================================
// 3. BOYUT VE PADDING MODIFIER'LARI
// ============================================================================

/**
 * ## Boyut ve Padding Modifier'ları
 *
 * Compose'da boyut ve padding ayarlamak için kullanılan temel modifier'lar
 */

@Composable
fun SizeAndPaddingExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF0F8FF), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Boyut ve Padding Modifier'ları",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Size modifier örneği
        Row(
            modifier = Modifier
                .size(width = 300.dp, height = 80.dp)
                .background(Color.Blue, RoundedCornerShape(8.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                "Sabit boyut: 300x80dp",
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // RequiredSize örneği
        Row(
            modifier = Modifier
                .size(width = 200.dp, height = 60.dp)
                .background(Color.Green, RoundedCornerShape(8.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.requiredSize(80.dp), // Parent'tan bağımsız boyut
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                "RequiredSize: 80dp",
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // FillMaxHeight örneği
        Row(
            modifier = Modifier
                .size(width = 250.dp, height = 100.dp)
                .background(Color(0xFF800080), RoundedCornerShape(8.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(), // Parent'ın yüksekliğini doldur
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                "FillMaxHeight",
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * ## PaddingFromBaseline Örneği
 *
 * Text baseline'ından padding ekleme
 */

@Composable
fun PaddingFromBaselineExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF5F5DC), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "PaddingFromBaseline Örneği",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column {
            Text(
                text = "Normal Text",
                fontSize = 16.sp
            )
            Text(
                text = "Baseline'dan 50dp padding",
                fontSize = 16.sp,
                modifier = Modifier.paddingFromBaseline(top = 50.dp)
            )
        }
    }
}

// ============================================================================
// 4. OFFSET MODIFIER'I
// ============================================================================

/**
 * ## Offset Modifier'ı
 *
 * Bir layout'u orijinal pozisyonuna göre konumlandırmak için offset modifier'ı kullanın.
 * Offset, padding'den farklı olarak ölçümleri değiştirmez.
 */

@Composable
fun OffsetExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFE6E6FA), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Offset Modifier Örneği",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            // Normal pozisyon
            Text(
                text = "Normal",
                modifier = Modifier
                    .background(Color.Red, RoundedCornerShape(4.dp))
                    .padding(8.dp),
                color = Color.White
            )

            // Offset ile kaydırılmış
            Text(
                text = "Offset(20dp, 30dp)",
                modifier = Modifier
                    .offset(x = 20.dp, y = 30.dp)
                    .background(Color.Blue, RoundedCornerShape(4.dp))
                    .padding(8.dp),
                color = Color.White
            )

            // Negatif offset
            Text(
                text = "Offset(-10dp, -10dp)",
                modifier = Modifier
                    .offset(x = (-10).dp, y = (-10).dp)
                    .background(Color.Green, RoundedCornerShape(4.dp))
                    .padding(8.dp),
                color = Color.White
            )
        }
    }
}

// ============================================================================
// 5. SCOPED MODIFIER'LAR
// ============================================================================

/**
 * ## Scoped Modifier'lar
 *
 * Compose'da bazı modifier'lar sadece belirli composable'ların çocuklarında kullanılabilir.
 * Compose bunu custom scope'lar aracılığıyla zorlar.
 */

/**
 * ## BoxScope Modifier'ları
 *
 * matchParentSize modifier'ı sadece Box'un doğrudan çocuklarında kullanılabilir.
 */

@Composable
fun BoxScopeExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF0FFF0), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "BoxScope Modifier'ları",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // matchParentSize örneği
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color(0xFFADD8E6), RoundedCornerShape(8.dp))
        ) {
            // Spacer, Box'un boyutunu alır ama Box'un boyutunu etkilemez
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            )

            // Ana içerik
            Text(
                text = "matchParentSize",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Blue,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Kırmızı alan matchParentSize kullanıyor",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

/**
 * ## RowScope ve ColumnScope Modifier'ları
 *
 * weight modifier'ı sadece Row ve Column'un doğrudan çocuklarında kullanılabilir.
 */

@Composable
fun RowColumnScopeExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFFFF8DC), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "RowScope ve ColumnScope Modifier'ları",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Row weight örneği
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(2f) // 2/3 genişlik
                    .fillMaxHeight()
                    .background(Color.Red, RoundedCornerShape(4.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Weight 2f", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Box(
                modifier = Modifier
                    .weight(1f) // 1/3 genişlik
                    .fillMaxHeight()
                    .background(Color.Blue, RoundedCornerShape(4.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Weight 1f", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Kırmızı alan 2x, mavi alan 1x genişlik",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

// ============================================================================
// 6. ETKİLEŞİM MODIFIER'LARI
// ============================================================================

/**
 * ## Etkileşim Modifier'ları
 *
 * Kullanıcı etkileşimlerini işlemek için kullanılan modifier'lar
 */

@Composable
fun InteractionModifiersExample() {
    var clickCount by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFFDF5E6), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Etkileşim Modifier'ları",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Clickable örneği
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { clickCount++ }
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.Blue
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Tıklanabilir Kart",
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Tıklama sayısı: $clickCount",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Scrollable örneği
        Text(
            text = "Kaydırılabilir İçerik:",
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .verticalScroll(scrollState)
                .padding(12.dp)
        ) {
            Column {
                repeat(10) { index ->
                    Text(
                        text = "Kaydırılabilir öğe ${index + 1}",
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}

// ============================================================================
// 7. MODIFIER'LARI ÇIKARMA VE YENİDEN KULLANMA
// ============================================================================

/**
 * ## Modifier'ları Çıkarma ve Yeniden Kullanma
 *
 * Aynı modifier zincirini birden fazla composable'da kullanmak için
 * bunları değişkenlere çıkarabilirsiniz.
 */

// Yeniden kullanılabilir modifier'lar
val reusableCardModifier = Modifier
    .fillMaxWidth()
    .background(Color.White, RoundedCornerShape(8.dp))
    .padding(16.dp)

val reusableButtonModifier = Modifier
    .background(Color.Blue, RoundedCornerShape(4.dp))
    .padding(horizontal = 16.dp, vertical = 8.dp)

@Composable
fun ModifierReuseExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Modifier Yeniden Kullanımı",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Aynı modifier'ı kullanan kartlar
        Card(
            modifier = reusableCardModifier,
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Text("Kart 1 - Aynı modifier")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = reusableCardModifier,
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Text("Kart 2 - Aynı modifier")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Buton modifier'ı
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Buton 1",
                modifier = reusableButtonModifier,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Buton 2",
                modifier = reusableButtonModifier,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * ## Scoped Modifier'ları Çıkarma
 *
 * Scoped modifier'ları en yüksek seviyeye çıkararak yeniden kullanabilirsiniz.
 */

@Composable
fun ScopedModifierReuseExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFE8F5E8), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Scoped Modifier Yeniden Kullanımı",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Column scope'unda weight modifier'ı çıkarma
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val reusableWeightModifier = Modifier
                .weight(1f)
                .background(Color(0xFFADD8E6), RoundedCornerShape(4.dp))
                .padding(12.dp)

            Text(
                text = "Weight 1f - Öğe 1",
                modifier = reusableWeightModifier,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Weight 1f - Öğe 2",
                modifier = reusableWeightModifier,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ============================================================================
// 8. PERFORMANCE İPUÇLARI
// ============================================================================

/**
 * ## Modifier Performance İpuçları
 *
 * Modifier'ları yeniden kullanmak performansı artırır ve kod temizliği sağlar.
 */

@Composable
fun ModifierPerformanceExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Modifier Performance İpuçları",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "✅ İyi: Modifier'ları değişkenlere çıkarın",
            fontSize = 14.sp,
            color = Color.Green
        )

        Text(
            text = "val reusableModifier = Modifier.padding(12.dp).background(Color.Gray)",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "❌ Kötü: Her recomposition'da yeni modifier oluşturma",
            fontSize = 14.sp,
            color = Color.Red
        )

        Text(
            text = "modifier = Modifier.padding(12.dp).background(Color.Gray)",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "💡 İpucu: Modifier sırası önemlidir!",
            fontSize = 14.sp,
            color = Color.Blue
        )

        Text(
            text = "padding -> background -> clickable (tüm alan tıklanabilir)",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

// ============================================================================
// 9. PREVIEW VE TEST
// ============================================================================

@Preview(showBackground = true, heightDp = 3000)
@Composable
fun ModifiersPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Compose Modifiers Örnekleri",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            BasicModifierExample()
            ModifierChainingExample()
            ModifierOrderExample()
            SizeAndPaddingExample()
            PaddingFromBaselineExample()
            OffsetExample()
            BoxScopeExample()
            RowColumnScopeExample()
            InteractionModifiersExample()
            ModifierReuseExample()
            ScopedModifierReuseExample()
            ModifierPerformanceExample()
        }
    }
}

// Ayrı ayrı preview'lar
@Preview(showBackground = true)
@Composable
fun BasicModifierExamplePreview() {
    MaterialTheme {
        BasicModifierExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ModifierChainingExamplePreview() {
    MaterialTheme {
        ModifierChainingExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ModifierOrderExamplePreview() {
    MaterialTheme {
        ModifierOrderExample()
    }
}

@Preview(showBackground = true)
@Composable
fun SizeAndPaddingExamplePreview() {
    MaterialTheme {
        SizeAndPaddingExample()
    }
}

@Preview(showBackground = true)
@Composable
fun OffsetExamplePreview() {
    MaterialTheme {
        OffsetExample()
    }
}

@Preview(showBackground = true)
@Composable
fun BoxScopeExamplePreview() {
    MaterialTheme {
        BoxScopeExample()
    }
}

@Preview(showBackground = true)
@Composable
fun RowColumnScopeExamplePreview() {
    MaterialTheme {
        RowColumnScopeExample()
    }
}

@Preview(showBackground = true)
@Composable
fun InteractionModifiersExamplePreview() {
    MaterialTheme {
        InteractionModifiersExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ModifierReuseExamplePreview() {
    MaterialTheme {
        ModifierReuseExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ScopedModifierReuseExamplePreview() {
    MaterialTheme {
        ScopedModifierReuseExample()
    }
}

/**
 * ## Özet ve Önemli Noktalar
 *
 * ### Modifier'lar:
 * - **Temel:** padding, size, background, offset
 * - **Scoped:** matchParentSize (Box), weight (Row/Column)
 * - **Etkileşim:** clickable, scrollable
 * - **Performance:** Modifier'ları çıkarıp yeniden kullanma
 * - **Sıra:** Modifier sırası sonucu etkiler
 *
 * ### Temel Kavramlar:
 * - Modifier'lar standart Kotlin nesneleridir
 * - Zincirleme ile birleştirilebilir
 * - Sıra önemlidir - her modifier öncekini etkiler
 * - Scoped modifier'lar belirli composable'larda kullanılabilir
 * - Performance için modifier'ları yeniden kullanın
 *
 * ### Best Practices:
 * - Modifier'ları değişkenlere çıkarın
 * - Scoped modifier'ları doğru scope'da kullanın
 * - Modifier sırasını dikkatli planlayın
 * - Büyük projelerde modifier'ları yeniden kullanın
 *
 * Bu örnekler Compose Modifier sisteminin temel kavramlarını kapsar.
 */
