package com.dilara.composeexample.applayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * # Constraints ve Modifier Order - Kapsamlı Rehber
 *
 * Compose'da birden fazla modifier'ı zincirleyerek composable'ların görünümünü ve
 * davranışını değiştirebilirsiniz. Bu modifier zincirleri, composable'lara geçirilen
 * constraints'leri etkileyebilir - bunlar genişlik ve yükseklik sınırlarını tanımlar.
 *
 * Bu doküman, zincirlenmiş modifier'ların constraints'leri nasıl etkilediğini ve
 * bunun da composable'ların ölçümü ve yerleştirilmesini nasıl etkilediğini açıklar.
 *
 * ## UI Tree'de Modifier'lar
 *
 * Modifier'ların birbirini nasıl etkilediğini anlamak için, bunların composition
 * aşamasında oluşturulan UI tree'de nasıl göründüğünü görselleştirmek faydalıdır.
 *
 * UI tree'de modifier'ları layout node'ları için wrapper node'lar olarak görselleştirebilirsiniz.
 */

// ============================================================================
// 1. TEMEL CONSTRAINTS KAVRAMLARI
// ============================================================================

/**
 * ## Constraint Türleri
 *
 * Compose'da üç temel constraint türü vardır:
 * 1. **Bounded** - Minimum ve maksimum sınırlar
 * 2. **Unbounded** - Sınırsız (infinity)
 * 3. **Exact** - Tam boyut gereksinimi
 */

@Composable
fun ConstraintTypesExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF0F8FF), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Constraint Türleri",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Bounded constraints örneği
        Text(
            text = "1. Bounded Constraints (Sınırlı)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Blue
        )
        Text(
            text = "Minimum ve maksimum genişlik/yükseklik sınırları var",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Unbounded constraints örneği
        Text(
            text = "2. Unbounded Constraints (Sınırsız)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Green
        )
        Text(
            text = "Maksimum genişlik/yükseklik infinity (sınırsız)",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Exact constraints örneği
        Text(
            text = "3. Exact Constraints (Tam)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Red
        )
        Text(
            text = "Minimum ve maksimum aynı değer (tam boyut)",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

// ============================================================================
// 2. SIZE MODIFIER'I VE CONSTRAINTS
// ============================================================================

/**
 * ## Size Modifier'ı
 *
 * Size modifier'ı, içeriğin tercih edilen boyutunu belirtir.
 * Gelen constraints'leri kendisine geçirilen değere uyacak şekilde adapte eder.
 */

@Composable
fun SizeModifierExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFE6F3FF), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Size Modifier Örnekleri",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Normal size kullanımı
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Blue, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "100x100dp",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Size modifier'ın constraints'i nasıl etkilediği
        Text(
            text = "Size modifier constraints'leri adapte eder:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "• Gelen constraints: 50-300dp genişlik",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Size(100dp) → Constraints: 100dp (exact)",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

/**
 * ## RequiredSize Modifier'ı
 *
 * RequiredSize modifier'ı, gelen constraints'leri geçersiz kılar ve
 * belirttiğiniz boyutu exact bounds olarak geçirir.
 */

@Composable
fun RequiredSizeExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFFFF0E6), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "RequiredSize Modifier",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // RequiredSize kullanımı
        Box(
            modifier = Modifier
                .size(80.dp) // Parent constraint'i
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(120.dp) // Parent'ı geçersiz kılar
                    .background(Color.Red, RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "120dp\n(required)",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "RequiredSize özellikleri:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "• Gelen constraints'leri geçersiz kılar",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Child ortalanır (centered)",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

// ============================================================================
// 3. WIDTH VE HEIGHT MODIFIER'LARI
// ============================================================================

/**
 * ## Width ve Height Modifier'ları
 *
 * Size modifier'ı hem genişlik hem de yüksekliği adapte eder.
 * Width modifier'ı ile sabit genişlik ayarlayabilir, yüksekliği kararsız bırakabilirsiniz.
 * Height modifier'ı ile de aynı şekilde sabit yükseklik ayarlayabilirsiniz.
 */

@Composable
fun WidthHeightModifiersExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF0FFF0), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Width ve Height Modifier'ları",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Width modifier örneği
        Text(
            text = "Width Modifier (Sabit genişlik):",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Box(
            modifier = Modifier
                .width(150.dp) // Sabit genişlik
                .height(60.dp) // Sabit yükseklik
                .background(Color.Blue, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "150dp genişlik",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Height modifier örneği
        Text(
            text = "Height Modifier (Sabit yükseklik):",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Box(
            modifier = Modifier
                .fillMaxWidth() // Genişlik sınırsız
                .height(80.dp) // Sabit yükseklik
                .background(Color.Green, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "80dp yükseklik",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ============================================================================
// 4. SIZEIN MODIFIER'I
// ============================================================================

/**
 * ## SizeIn Modifier'ı
 *
 * SizeIn modifier'ı, genişlik ve yükseklik için exact minimum ve maksimum
 * constraints ayarlamanızı sağlar. Constraints üzerinde ince ayarlı kontrol
 * gerektiğinde kullanın.
 */

@Composable
fun SizeInModifierExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF5F0FF), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "SizeIn Modifier",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // SizeIn kullanımı
        Box(
            modifier = Modifier
                .sizeIn(
                    minWidth = 100.dp,
                    maxWidth = 200.dp,
                    minHeight = 50.dp,
                    maxHeight = 100.dp
                )
                .background(Color(0xFF800080), RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "SizeIn\n100-200dp\n50-100dp",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "SizeIn özellikleri:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "• minWidth, maxWidth, minHeight, maxHeight",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• İnce ayarlı constraint kontrolü",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

// ============================================================================
// 5. MODIFIER ORDER ÖNEMİ
// ============================================================================

/**
 * ## Modifier Sırasının Önemi
 *
 * Modifier'ların sırası çok önemlidir çünkü her modifier, önceki modifier'ın
 * döndürdüğü constraints'leri alır ve bunları değiştirir.
 */

@Composable
fun ConstraintsModifierOrderExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFFFE4E1), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Modifier Sırası Örnekleri",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Örnek 1: fillMaxSize -> size
        Text(
            text = "1. fillMaxSize() -> size(50.dp)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Blue
        )

        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize() // Constraints: 120x120dp (exact)
                    .size(50.dp)   // Constraints: 120x120dp (fillMaxSize kazanır)
                    .background(Color.Blue, RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "120x120dp\n(fillMaxSize kazanır)",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Örnek 2: fillMaxSize -> wrapContentSize -> size
        Text(
            text = "2. fillMaxSize() -> wrapContentSize() -> size(50.dp)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Green
        )

        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()      // Constraints: 120x120dp (exact)
                    .wrapContentSize()  // Constraints: 0-120dp (bounded)
                    .size(50.dp)        // Constraints: 50x50dp (exact)
                    .background(Color.Green, RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "50x50dp\n(ortalanmış)",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }
    }
}

// ============================================================================
// 6. WRAPCONTENTSIZE MODIFIER'I
// ============================================================================

/**
 * ## WrapContentSize Modifier'ı
 *
 * WrapContentSize modifier'ı, minimum constraints'leri sıfırlar.
 * Bu sayede child, mevcut alanın tamamını kaplayabilir veya daha küçük olabilir.
 * Ayrıca child'ı mevcut minimum bounds'ların merkezine yerleştirir.
 */

@Composable
fun WrapContentSizeExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFE6FFE6), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "WrapContentSize Modifier",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "WrapContentSize özellikleri:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "• Minimum constraints'leri sıfırlar",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Child'ı merkeze yerleştirir",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Child'ın boyutunu parent'a bildirir",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // WrapContentSize örneği
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()      // Constraints: 150x150dp (exact)
                    .wrapContentSize()  // Constraints: 0-150dp (bounded)
                    .size(80.dp)        // Constraints: 80x80dp (exact)
                    .background(Color(0xFFFFA500), RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "80x80dp\n(ortalanmış)",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

// ============================================================================
// 7. CLIP VE PADDING SIRASI ÖNEMİ
// ============================================================================

/**
 * ## Clip ve Padding Sırası
 *
 * Clip ve padding modifier'larının sırası görsel sonucu etkiler.
 * Yanlış sıra beklenmeyen sonuçlara yol açabilir.
 */

@Composable
fun ClipPaddingOrderExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFFFF0F5), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Clip ve Padding Sırası",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Yanlış sıra
        Text(
            text = "❌ Yanlış Sıra: clip() -> padding() -> size()",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Red
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)  // 100x100dp canvas
                    .padding(10.dp)     // 80x80dp canvas
                    .size(60.dp)        // 60x60dp content
                    .background(Color.Red, RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "Yanlış\nsıra",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Doğru sıra
        Text(
            text = "✅ Doğru Sıra: padding() -> clip() -> size()",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Green
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)     // 80x80dp canvas
                    .clip(CircleShape)  // 80x80dp circle
                    .size(60.dp)        // 60x60dp content
                    .background(Color.Green, RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "Doğru\nsıra",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }
    }
}

// ============================================================================
// 8. CONSTRAINTS FLOW ÖRNEĞİ
// ============================================================================

/**
 * ## Constraints Flow Örneği
 *
 * Constraints'lerin parent'tan child'a nasıl aktarıldığını gösteren örnek.
 */

@Composable
fun ConstraintsFlowExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF0F8FF), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Constraints Flow",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Constraints akışı:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "1. Parent constraints'leri child'a geçirir",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "2. Child kendi boyutunu belirler",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "3. Child boyutunu parent'a bildirir",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "4. Parent kendi boyutunu belirler",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Örnek constraint flow
        Box(
            modifier = Modifier
                .size(200.dp) // Parent constraint: 200x200dp (exact)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Constraint: 200dp genişlik (exact)
                    .height(100.dp) // Constraint: 100dp yükseklik (exact)
                    .background(Color.Blue, RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "200x100dp\n(parent constraint'i)",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

// ============================================================================
// 9. PERFORMANCE İPUÇLARI
// ============================================================================

/**
 * ## Constraints ve Performance İpuçları
 *
 * Constraints'leri doğru anlamak performansı artırır ve beklenmeyen davranışları önler.
 */

@Composable
fun ConstraintsPerformanceExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Constraints Performance İpuçları",
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
            text = "• Modifier sırasını dikkatli planlayın",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Constraints'leri anlayın",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• requiredSize() gerektiğinde kullanın",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• wrapContentSize() ile ortalamayı öğrenin",
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
            text = "• Çoklu size() modifier'ları",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Yanlış modifier sırası",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "• Constraints'leri görmezden gelmek",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

// ============================================================================
// 10. PREVIEW VE TEST
// ============================================================================

@Preview(showBackground = true, heightDp = 4000)
@Composable
fun ConstraintsAndModifierOrderPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Constraints ve Modifier Order Örnekleri",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ConstraintTypesExample()
            SizeModifierExample()
            RequiredSizeExample()
            WidthHeightModifiersExample()
            SizeInModifierExample()
            ConstraintsModifierOrderExample()
            WrapContentSizeExample()
            ClipPaddingOrderExample()
            ConstraintsFlowExample()
            ConstraintsPerformanceExample()
        }
    }
}

// Ayrı ayrı preview'lar
@Preview(showBackground = true)
@Composable
fun ConstraintTypesExamplePreview() {
    MaterialTheme {
        ConstraintTypesExample()
    }
}

@Preview(showBackground = true)
@Composable
fun SizeModifierExamplePreview() {
    MaterialTheme {
        SizeModifierExample()
    }
}

@Preview(showBackground = true)
@Composable
fun RequiredSizeExamplePreview() {
    MaterialTheme {
        RequiredSizeExample()
    }
}

@Preview(showBackground = true)
@Composable
fun WidthHeightModifiersExamplePreview() {
    MaterialTheme {
        WidthHeightModifiersExample()
    }
}

@Preview(showBackground = true)
@Composable
fun SizeInModifierExamplePreview() {
    MaterialTheme {
        SizeInModifierExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintsModifierOrderExamplePreview() {
    MaterialTheme {
        ConstraintsModifierOrderExample()
    }
}

@Preview(showBackground = true)
@Composable
fun WrapContentSizeExamplePreview() {
    MaterialTheme {
        WrapContentSizeExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ClipPaddingOrderExamplePreview() {
    MaterialTheme {
        ClipPaddingOrderExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintsFlowExamplePreview() {
    MaterialTheme {
        ConstraintsFlowExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintsPerformanceExamplePreview() {
    MaterialTheme {
        ConstraintsPerformanceExample()
    }
}

/**
 * ## Özet ve Önemli Noktalar
 *
 * ### Constraint Türleri:
 * - **Bounded:** Minimum ve maksimum sınırlar
 * - **Unbounded:** Sınırsız (infinity)
 * - **Exact:** Tam boyut gereksinimi
 * - **Combination:** Yukarıdakilerin kombinasyonu
 *
 * ### Temel Modifier'lar:
 * - **size():** Tercih edilen boyut (constraints'i adapte eder)
 * - **requiredSize():** Constraints'i geçersiz kılar
 * - **width()/height():** Tek boyut kontrolü
 * - **sizeIn():** İnce ayarlı constraint kontrolü
 * - **wrapContentSize():** Minimum constraints'i sıfırlar ve ortalar
 *
 * ### Modifier Sırası:
 * - Her modifier önceki modifier'ın constraints'lerini alır
 * - Sıra görsel sonucu etkiler
 * - fillMaxSize() -> wrapContentSize() -> size() kombinasyonu yaygın
 *
 * ### Performance İpuçları:
 * - Modifier sırasını dikkatli planlayın
 * - Constraints'leri anlayın
 * - requiredSize() gerektiğinde kullanın
 * - wrapContentSize() ile ortalamayı öğrenin
 *
 * ### Best Practices:
 * - Constraints flow'unu anlayın
 * - Modifier sırasını test edin
 * - Görsel sonuçları kontrol edin
 * - Performance'ı göz önünde bulundurun
 */
