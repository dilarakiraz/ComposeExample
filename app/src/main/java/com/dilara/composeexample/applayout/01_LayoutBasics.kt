package com.dilara.composeexample.applayout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * # Jetpack Compose Layout Basics
 *
 * Jetpack Compose, uygulamanızın UI'sini tasarlamayı ve oluşturmayı çok daha kolay hale getirir.
 * Compose, state'i UI elementlerine şu yollarla dönüştürür:
 *
 * 1. **Composition** - Elementlerin oluşturulması
 * 2. **Layout** - Elementlerin yerleştirilmesi
 * 3. **Drawing** - Elementlerin çizilmesi
 *
 * Bu doküman, elementlerin layout'u üzerine odaklanır ve UI elementlerinizi düzenlemenize
 * yardımcı olmak için Compose'un sağladığı temel yapı taşlarını açıklar.
 *
 * ## Compose Layout Sisteminin Hedefleri
 *
 * 1. **Yüksek Performans** - Tek ölçüm ile derin UI ağaçlarını verimli işleme
 * 2. **Kolay Custom Layout Yazma** - Kendi layout'larınızı kolayca oluşturma
 *
 * **Not:** Android View sisteminde RelativeLayout gibi belirli View'ları iç içe geçirirken
 * performans sorunları yaşayabilirdiniz. Compose çoklu ölçümden kaçındığı için, performansı
 * etkilemeden istediğiniz kadar derinlikte iç içe geçirebilirsiniz.
 */

// ============================================================================
// 1. TEMEL COMPOSABLE FONKSİYONLARI
// ============================================================================

/**
 * ## Temel Composable Fonksiyonları
 *
 * Composable fonksiyonları Compose'un temel yapı taşlarıdır. Bir composable fonksiyon,
 * UI'nizin bir kısmını açıklayan Unit yayan bir fonksiyondur.
 *
 * **Problem:** Eğer elementlerin nasıl düzenleneceği konusunda rehberlik sağlamazsanız,
 * Compose bunları istemediğiniz şekilde düzenleyebilir.
 */

@Composable
fun ProblematicLayout() {
    // Bu kod iki text element üretir ama nasıl düzenleneceklerini belirtmez
    Text("Alfred Sisley")
    Text("3 dakika önce")
    // Sonuç: Text'ler birbirinin üzerine yığılır ve okunamaz hale gelir
}

/**
 * **Çözüm:** Compose, UI elementlerinizi düzenlemenize yardımcı olacak hazır layout'lar
 * sağlar ve kendi özelleştirilmiş layout'larınızı tanımlamayı kolaylaştırır.
 */

// ============================================================================
// 2. STANDART LAYOUT BİLEŞENLERİ
// ============================================================================

/**
 * ## Column - Dikey Düzenleme
 *
 * Column, öğeleri ekranda dikey olarak yerleştirmek için kullanılır.
 * Android'deki LinearLayout (vertical orientation) ile benzerdir.
 */

@Composable
fun ColumnExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Column Örneği",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Alfred Sisley")
        Text("3 dakika önce")
        Text("Sanatçı")
    }
}

/**
 * ## Row - Yatay Düzenleme
 *
 * Row, öğeleri ekranda yatay olarak yerleştirmek için kullanılır.
 * Android'deki LinearLayout (horizontal orientation) ile benzerdir.
 */

@Composable
fun RowExample() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFADD8E6), RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.Person,
            contentDescription = "Profil",
            tint = Color.Blue
        )
        Column {
            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
            Text("3 dakika önce", fontSize = 12.sp)
        }
        Icon(
            Icons.Default.MoreVert,
            contentDescription = "Daha fazla",
            tint = Color.Gray
        )
    }
}

/**
 * ## Box - Üst Üste Yerleştirme
 *
 * Box, elementleri birbirinin üzerine koymak için kullanılır.
 * Android'deki FrameLayout ile benzerdir.
 */

@Composable
fun BoxExample() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color(0xFF90EE90), CircleShape)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Ana içerik
        Icon(
            Icons.Default.Person,
            contentDescription = "Profil Resmi",
            modifier = Modifier.size(60.dp),
            tint = Color.White
        )

        // Üst üste gelecek içerik (Badge)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(24.dp)
                .background(Color.Red, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "3",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ============================================================================
// 3. LAYOUT DÜZENLEME VE HİZALAMA
// ============================================================================

/**
 * ## Column ve Row Düzenleme Parametreleri
 *
 * - **Row için:** horizontalArrangement ve verticalAlignment
 * - **Column için:** verticalArrangement ve horizontalAlignment
 */

@Composable
fun ArrangementExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFFFFACD), RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Düzenleme Örneği", fontWeight = FontWeight.Bold)

        // Farklı arrangement örnekleri
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Sol")
            Text("Orta")
            Text("Sağ")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Ortalanmış")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text("Sağa hizalı")
        }
    }
}

// ============================================================================
// 4. MODIFIER KULLANIMI İLE LAYOUT
// ============================================================================

/**
 * ## Modifier ile Layout Özelleştirme
 *
 * Modifier'lar, composable'larınızı dekore etmek veya geliştirmek için kullanılır.
 * Layout özelleştirmesi için temel araçlardır.
 */

@Composable
fun ModifierLayoutExample() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { /* Tıklama işlemi */ }
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Blue, CircleShape)
                        .padding(12.dp),
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "Alfred Sisley",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        "Sanatçı",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }

                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "Seçenekler",
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Son görülme: 3 dakika önce",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

// ============================================================================
// 5. RESPONSIVE LAYOUTS VE CONSTRAINTS
// ============================================================================

/**
 * ## BoxWithConstraints ile Responsive Layout
 *
 * Farklı ekran boyutlarına uyum sağlamak için BoxWithConstraints kullanılır.
 */

@Composable
fun ResponsiveLayoutExample() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFE0FFFF), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                "Responsive Layout",
                fontWeight = FontWeight.Bold
            )
            Text("Min Yükseklik: ${this@BoxWithConstraints.minHeight.value}dp")
            Text("Max Genişlik: ${this@BoxWithConstraints.maxWidth.value}dp")

            Spacer(modifier = Modifier.height(8.dp))

            // Ekran boyutuna göre farklı layout
            if (this@BoxWithConstraints.maxWidth > 600.dp) {
                // Tablet veya büyük ekran için yatay layout
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CardExample("Kart 1")
                    CardExample("Kart 2")
                    CardExample("Kart 3")
                }
            } else {
                // Telefon için dikey layout
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CardExample("Kart 1")
                    Spacer(modifier = Modifier.height(8.dp))
                    CardExample("Kart 2")
                    Spacer(modifier = Modifier.height(8.dp))
                    CardExample("Kart 3")
                }
            }
        }
    }
}

@Composable
fun CardExample(title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ============================================================================
// 6. SCAFFOLD VE SLOT-BASED LAYOUTS
// ============================================================================

/**
 * ## Scaffold ile Slot-Based Layout
 *
 * Scaffold, Material Design'ın temel layout yapısını sağlar ve slot API'ları kullanır.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldExample() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Layout Örnekleri") },
                actions = {
                    IconButton(onClick = { /* Bildirimler */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Bildirimler")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Ana Sayfa") },
                    label = { Text("Ana Sayfa") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Arama") },
                    label = { Text("Arama") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profil") },
                    label = { Text("Profil") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Yeni içerik ekle */ }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Ekle")
            }
        }
    ) { paddingValues ->
        // Ana içerik
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when (selectedTab) {
                0 -> HomeContent()
                1 -> SearchContent()
                2 -> ProfileContent()
            }
        }
    }
}

@Composable
fun HomeContent() {
    LazyColumn {
        items(10) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Article,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.Blue, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Makale ${index + 1}",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            "Bu bir örnek makale açıklamasıdır",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchContent() {
    Column {
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Arama") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Arama sonuçları burada görünecek",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp
        )
    }
}

@Composable
fun ProfileContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Blue, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Kullanıcı Adı",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Text(
            "user@example.com",
            color = Color.Gray
        )
    }
}

// ============================================================================
// 7. CUSTOM LAYOUT ÖRNEĞİ
// ============================================================================

/**
 * ## Custom Layout Oluşturma
 *
 * Compose'da kendi layout'larınızı oluşturabilirsiniz.
 */

@Composable
fun CustomLayoutExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFFFB6C1), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            "Custom Layout Örneği",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Custom layout kullanımı
        CustomHorizontalLayout {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Red, RoundedCornerShape(8.dp))
            )
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Green, RoundedCornerShape(8.dp))
            )
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Blue, RoundedCornerShape(8.dp))
            )
        }
    }
}

@Composable
fun CustomHorizontalLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Ölçüm aşaması
        val placeables = measurables.map { it.measure(constraints) }

        // Layout boyutlarını hesapla
        val totalWidth = placeables.sumOf { it.width }
        val maxHeight = placeables.maxOfOrNull { it.height } ?: 0

        // Yerleştirme aşaması
        layout(totalWidth, maxHeight) {
            var x = 0
            placeables.forEach { placeable ->
                placeable.place(x, 0)
                x += placeable.width
            }
        }
    }
}

// ============================================================================
// 8. PERFORMANCE VE BEST PRACTICES
// ============================================================================

/**
 * ## Performance İpuçları
 *
 * 1. **Tek Ölçüm:** Compose, çocukları sadece bir kez ölçer (yüksek performans)
 * 2. **Derin İç İçe Geçme:** Performance sorunu olmadan istediğiniz kadar derinleştirebilirsiniz
 * 3. **Lazy Loading:** Büyük listeler için LazyColumn/LazyRow kullanın
 * 4. **Modifier Chaining:** Modifier'ları doğru sırada kullanın
 */

@Composable
fun PerformanceExample() {
    // ✅ İyi: LazyColumn kullanımı
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(1000) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Item $index",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    // ❌ Kötü: Column ile 1000 item (performans sorunu)
    // Column {
    //     repeat(1000) { index ->
    //         Text("Item $index")
    //     }
    // }
}

// ============================================================================
// 9. PREVIEW VE TEST
// ============================================================================

@Preview(showBackground = true, heightDp = 2000)
@Composable
fun LayoutBasicsPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Compose Layout Basics Örnekleri",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ColumnExample()
            RowExample()
            BoxExample()
            ArrangementExample()
            ModifierLayoutExample()
            ResponsiveLayoutExample()
            CustomLayoutExample()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScaffoldPreview() {
    MaterialTheme {
        ScaffoldExample()
    }
}

// Ayrı ayrı preview'lar
@Preview(showBackground = true)
@Composable
fun ColumnExamplePreview() {
    MaterialTheme {
        ColumnExample()
    }
}

@Preview(showBackground = true)
@Composable
fun RowExamplePreview() {
    MaterialTheme {
        RowExample()
    }
}

@Preview(showBackground = true)
@Composable
fun BoxExamplePreview() {
    MaterialTheme {
        BoxExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ModifierLayoutExamplePreview() {
    MaterialTheme {
        ModifierLayoutExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ArrangementExamplePreview() {
    MaterialTheme {
        ArrangementExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ResponsiveLayoutExamplePreview() {
    MaterialTheme {
        ResponsiveLayoutExample()
    }
}

@Preview(showBackground = true)
@Composable
fun CustomLayoutExamplePreview() {
    MaterialTheme {
        CustomLayoutExample()
    }
}

/**
 * ## Özet ve Önemli Noktalar
 *
 * ### Temel Layout Bileşenleri:
 * - **Column:** Dikey düzenleme
 * - **Row:** Yatay düzenleme
 * - **Box:** Üst üste yerleştirme
 *
 * ### Düzenleme Parametreleri:
 * - **Arrangement:** Elementler arası boşluk
 * - **Alignment:** Hizalama
 * - **Modifier:** Styling ve davranış
 *
 * ### Performance:
 * - Tek ölçüm ile yüksek performans
 * - Derin iç içe geçme sorun değil
 * - Lazy loading büyük listeler için
 *
 * ### Responsive Design:
 * - BoxWithConstraints ile ekran boyutuna uyum
 * - Farklı layout'lar farklı ekranlar için
 *
 * ### Custom Layout:}
 * - Layout composable ile kendi layout'larınız
 * - Measurement ve placement ayrımı
 *
 * Bu örnekler Compose Layout sisteminin temel kavramlarını kapsar.
 * Daha gelişmiş konular için diğer dosyalara bakın.
 */
