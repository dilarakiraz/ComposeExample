package com.dilara.composeexample.architecture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * # Compose ile Navigation
 *
 * Navigation component, Jetpack Compose uygulamaları için destek sağlar.
 * Navigation component'in altyapısından ve özelliklerinden yararlanarak
 * composable'lar arasında gezinme yapabilirsin.
 *
 * ## Navigation Temel Kavramları
 *
 * ### NavController
 * - Navigation işlemlerini kontrol eden ana sınıf
 * - Navigate, pop, back stack yönetimi
 * - Compose'da rememberNavController() ile oluşturulur
 *
 * ### NavHost
 * - Navigation graph'ını tanımlayan composable
 * - Hangi route'ların mevcut olduğunu belirler
 * - Start destination ile başlangıç ekranını belirler
 *
 * ### Route
 * - Her ekranın benzersiz kimliği
 * - String veya Serializable data class olabilir
 * - Deep link'ler için kullanılır
 *
 * ### NavBackStackEntry
 * - Back stack'teki her entry'yi temsil eder
 * - Arguments'a erişim sağlar
 * - SavedStateHandle ile state saklama
 *
 * ## Navigation Best Practices
 *
 * ### ✅ İyi Uygulamalar:
 * - Sadece ID gibi minimum bilgiyi geçir
 * - Complex objeleri data layer'da sakla
 * - Navigation callback'lerini parametre olarak geçir
 * - Test edilebilir composable'lar yaz
 *
 * ### ❌ Kötü Uygulamalar:
 * - Complex objeleri argument olarak geçirme
 * - NavController'ı doğrudan composable'a geçirme
 * - Navigation logic'i composable içinde yazma
 * - Test edilemeyen yapılar oluşturma
 *
 * ## Deep Links
 * - URL'ler ile uygulama içi navigasyon
 * - External app'lerden uygulamaya erişim
 * - Intent filter ile manifest'te tanımlanır
 *
 * ## Nested Navigation
 * - Alt navigation graph'ları
 * - Bottom navigation ile entegrasyon
 * - Hierarchical navigation yapısı
 *
 * ## Testing
 * - NavHost test edilebilir olmalı
 * - Navigation callback'leri test edilmeli
 * - TestNavHostController kullanılmalı
 * - UI testleri ile navigation doğrulanmalı
 *
 * ## Dependency Setup
 *
 * Navigation Compose kullanmak için build.gradle'a şu dependency'yi ekle:
 *
 * ```kotlin
 * dependencies {
 *     implementation "androidx.navigation:navigation-compose:2.7.5"
 * }
 * ```
 *
 * ## Serialization Setup
 *
 * Type-safe navigation için kotlinx-serialization ekle:
 *
 * ```kotlin
 * dependencies {
 *     implementation "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0"
 * }
 * ```
 */

// ============================================================================
// 1. TEMEL NAVIGATION ÖRNEKLERİ
// ============================================================================

enum class Screen { HOME, PROFILE, SETTINGS } // Basit ekran enum'u

@Composable
fun SimpleNavigationExample() {
    var currentScreen by remember { mutableStateOf(Screen.HOME) } // Mevcut ekran

    when (currentScreen) { // Ekrana göre composable göster
        Screen.HOME -> HomeScreen(
            onNavigateToProfile = { currentScreen = Screen.PROFILE }, // Profil'e git
            onNavigateToSettings = { currentScreen = Screen.SETTINGS } // Ayarlar'a git
        )

        Screen.PROFILE -> ProfileScreen(
            onNavigateBack = { currentScreen = Screen.HOME } // Geri git
        )

        Screen.SETTINGS -> SettingsScreen(
            onNavigateBack = { currentScreen = Screen.HOME } // Geri git
        )
    }
}

@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit, // Profil'e git callback
    onNavigateToSettings: () -> Unit // Ayarlar'a git callback
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Tam boyut + padding
        verticalArrangement = Arrangement.Center, // Dikey ortalama
        horizontalAlignment = Alignment.CenterHorizontally // Yatay ortalama
    ) {
        Text("Ana Sayfa", fontSize = 24.sp, fontWeight = FontWeight.Bold) // Başlık
        Spacer(modifier = Modifier.height(32.dp)) // Boşluk
        Button(onClick = onNavigateToProfile, modifier = Modifier.fillMaxWidth()) { // Profil butonu
            Text("Profile Git") // Buton metni
        }
        Spacer(modifier = Modifier.height(16.dp)) // Boşluk
        Button(onClick = onNavigateToSettings, modifier = Modifier.fillMaxWidth()) { // Ayarlar butonu
            Text("Ayarlar") // Buton metni
        }
    }
}

@Composable
fun ProfileScreen(onNavigateBack: () -> Unit) { // Geri git callback
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Tam boyut + padding
        verticalArrangement = Arrangement.Center, // Dikey ortalama
        horizontalAlignment = Alignment.CenterHorizontally // Yatay ortalama
    ) {
        Text("Profil", fontSize = 24.sp, fontWeight = FontWeight.Bold) // Başlık
        Spacer(modifier = Modifier.height(32.dp)) // Boşluk
        Button(onClick = onNavigateBack, modifier = Modifier.fillMaxWidth()) { // Geri butonu
            Text("Geri") // Buton metni
        }
    }
}

@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) { // Geri git callback
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Tam boyut + padding
        verticalArrangement = Arrangement.Center, // Dikey ortalama
        horizontalAlignment = Alignment.CenterHorizontally // Yatay ortalama
    ) {
        Text("Ayarlar", fontSize = 24.sp, fontWeight = FontWeight.Bold) // Başlık
        Spacer(modifier = Modifier.height(32.dp)) // Boşluk
        Button(onClick = onNavigateBack, modifier = Modifier.fillMaxWidth()) { // Geri butonu
            Text("Geri") // Buton metni
        }
    }
}

// ============================================================================
// 2. BOTTOM NAVIGATION ÖRNEKLERİ
// ============================================================================

data class BottomNavItem(val title: String, val icon: ImageVector, val screen: Screen) // Bottom nav öğesi

@Composable
fun BottomNavigationExample() {
    var currentScreen by remember { mutableStateOf(Screen.HOME) } // Mevcut ekran
    val bottomNavItems = listOf( // Bottom navigation öğeleri
        BottomNavItem("Ana Sayfa", Icons.Default.Home, Screen.HOME), // Ana sayfa
        BottomNavItem("Profil", Icons.Default.Person, Screen.PROFILE), // Profil
        BottomNavItem("Ayarlar", Icons.Default.Settings, Screen.SETTINGS) // Ayarlar
    )

    Scaffold(
        bottomBar = { // Bottom bar
            NavigationBar {
                bottomNavItems.forEach { item -> // Her öğe için
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) }, // İkon
                        label = { Text(item.title) }, // Label
                        selected = currentScreen == item.screen, // Seçili mi?
                        onClick = { currentScreen = item.screen } // Ekranı değiştir
                    )
                }
            }
        }
    ) { innerPadding -> // İç padding
        Box(modifier = Modifier.padding(innerPadding)) { // Padding'i uygula
            when (currentScreen) { // Ekrana göre composable göster
                Screen.HOME -> HomeScreen(
                    onNavigateToProfile = { currentScreen = Screen.PROFILE }, // Profil'e git
                    onNavigateToSettings = { currentScreen = Screen.SETTINGS } // Ayarlar'a git
                )

                Screen.PROFILE -> ProfileScreen(
                    onNavigateBack = { currentScreen = Screen.HOME } // Geri git
                )

                Screen.SETTINGS -> SettingsScreen(
                    onNavigateBack = { currentScreen = Screen.HOME } // Geri git
                )
            }
        }
    }
}

// ============================================================================
// 3. DEEP LINKS ÖRNEKLERİ (Simulated)
// ============================================================================

@Composable
fun DeepLinkSimulationExample() {
    var currentScreen by remember { mutableStateOf(Screen.HOME) } // Mevcut ekran
    var deepLinkUrl by remember { mutableStateOf("") } // Deep link URL

    Column(modifier = Modifier.padding(16.dp)) { // İç boşluk
        Text("Deep Link Simülasyonu", fontSize = 20.sp, fontWeight = FontWeight.Bold) // Başlık
        Spacer(modifier = Modifier.height(16.dp)) // Boşluk
        OutlinedTextField(
            value = deepLinkUrl, // Deep link URL
            onValueChange = { deepLinkUrl = it }, // URL değişikliği
            label = { Text("Deep Link URL") }, // Label
            modifier = Modifier.fillMaxWidth() // Tam genişlik
        )
        Spacer(modifier = Modifier.height(16.dp)) // Boşluk
        Button(
            onClick = { // Deep link'i işle
                when { // URL'e göre ekranı değiştir
                    deepLinkUrl.contains("profile") -> currentScreen = Screen.PROFILE // Profil
                    deepLinkUrl.contains("settings") -> currentScreen = Screen.SETTINGS // Ayarlar
                    else -> currentScreen = Screen.HOME // Ana sayfa
                }
            },
            modifier = Modifier.fillMaxWidth() // Tam genişlik
        ) {
            Text("Deep Link'i İşle") // Buton metni
        }
        Spacer(modifier = Modifier.height(16.dp)) // Boşluk
        Text("Mevcut Ekran: $currentScreen") // Mevcut ekran
    }
}

// ============================================================================
// 4. NAVIGATION STATE MANAGEMENT
// ============================================================================

data class NavigationState(
    val currentScreen: Screen, // Mevcut ekran
    val backStack: List<Screen> = emptyList() // Back stack
)

@Composable
fun AdvancedNavigationExample() {
    var navigationState by remember { mutableStateOf(NavigationState(currentScreen = Screen.HOME)) } // Navigation state

    fun navigateTo(screen: Screen) { // Navigate fonksiyonu
        navigationState = navigationState.copy(
            currentScreen = screen, // Yeni ekran
            backStack = navigationState.backStack + navigationState.currentScreen // Back stack'e ekle
        )
    }

    fun navigateBack() { // Geri git fonksiyonu
        if (navigationState.backStack.isNotEmpty()) { // Back stack boş değilse
            val previousScreen = navigationState.backStack.last() // Önceki ekran
            navigationState = navigationState.copy(
                currentScreen = previousScreen, // Önceki ekrana git
                backStack = navigationState.backStack.dropLast(1) // Back stack'ten çıkar
            )
        }
    }

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) { // Navigation header
            Column(modifier = Modifier.padding(16.dp)) { // İç boşluk
                Text("Navigation State", fontWeight = FontWeight.Bold) // Başlık
                Text("Mevcut Ekran: ${navigationState.currentScreen}") // Mevcut ekran
                Text("Back Stack: ${navigationState.backStack}") // Back stack
            }
        }

        when (navigationState.currentScreen) { // Ekrana göre composable göster
            Screen.HOME -> HomeScreen(
                onNavigateToProfile = { navigateTo(Screen.PROFILE) }, // Profil'e git
                onNavigateToSettings = { navigateTo(Screen.SETTINGS) } // Ayarlar'a git
            )

            Screen.PROFILE -> ProfileScreen(
                onNavigateBack = { navigateBack() } // Geri git
            )

            Screen.SETTINGS -> SettingsScreen(
                onNavigateBack = { navigateBack() } // Geri git
            )
        }
    }
}

// ============================================================================
// 5. GERÇEK DÜNYA ÖRNEKLERİ
// ============================================================================

enum class ECommerceScreen { HOME, CATEGORY, PRODUCT, CART } // E-ticaret ekranları

@Composable
fun ECommerceNavigationExample() {
    var currentScreen by remember { mutableStateOf(ECommerceScreen.HOME) } // Mevcut ekran
    var selectedCategory by remember { mutableStateOf("") } // Seçili kategori
    var selectedProduct by remember { mutableStateOf("") } // Seçili ürün
    var cartItems by remember { mutableStateOf(listOf<String>()) } // Sepet öğeleri

    when (currentScreen) { // Ekrana göre composable göster
        ECommerceScreen.HOME -> ECommerceHomeScreen(
            onNavigateToCategory = { category -> // Kategori'ye git
                selectedCategory = category // Kategoriyi kaydet
                currentScreen = ECommerceScreen.CATEGORY // Kategori ekranına git
            },
            onNavigateToCart = { currentScreen = ECommerceScreen.CART } // Sepet'e git
        )

        ECommerceScreen.CATEGORY -> CategoryScreen(
            categoryName = selectedCategory, // Kategori adı
            onNavigateToProduct = { productId -> // Ürün'e git
                selectedProduct = productId // Ürün ID'sini kaydet
                currentScreen = ECommerceScreen.PRODUCT // Ürün ekranına git
            },
            onNavigateBack = { currentScreen = ECommerceScreen.HOME } // Geri git
        )

        ECommerceScreen.PRODUCT -> ProductScreen(
            productId = selectedProduct, // Ürün ID'si
            onNavigateBack = { currentScreen = ECommerceScreen.CATEGORY }, // Geri git
            onAddToCart = { productId -> // Sepete ekle
                cartItems = cartItems + productId // Sepete ürün ekle
            }
        )

        ECommerceScreen.CART -> CartScreen(
            items = cartItems, // Sepet öğeleri
            onNavigateBack = { currentScreen = ECommerceScreen.HOME } // Geri git
        )
    }
}

@Composable
fun ECommerceHomeScreen(
    onNavigateToCategory: (String) -> Unit, // Kategori'ye git callback
    onNavigateToCart: () -> Unit // Sepet'e git callback
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Tam boyut + padding
        verticalArrangement = Arrangement.Center, // Dikey ortalama
        horizontalAlignment = Alignment.CenterHorizontally // Yatay ortalama
    ) {
        Text("E-ticaret Ana Sayfa", fontSize = 24.sp, fontWeight = FontWeight.Bold) // Başlık
        Spacer(modifier = Modifier.height(32.dp)) // Boşluk
        Button(onClick = { onNavigateToCategory("Elektronik") }, modifier = Modifier.fillMaxWidth()) { // Elektronik kategorisi
            Text("Elektronik") // Buton metni
        }
        Spacer(modifier = Modifier.height(16.dp)) // Boşluk
        Button(onClick = { onNavigateToCategory("Giyim") }, modifier = Modifier.fillMaxWidth()) { // Giyim kategorisi
            Text("Giyim") // Buton metni
        }
        Spacer(modifier = Modifier.height(16.dp)) // Boşluk
        Button(onClick = onNavigateToCart, modifier = Modifier.fillMaxWidth()) { // Sepet'e git
            Text("Sepetim") // Buton metni
        }
    }
}

@Composable
fun CategoryScreen(
    categoryName: String, // Kategori adı
    onNavigateToProduct: (String) -> Unit, // Ürün'e git callback
    onNavigateBack: () -> Unit // Geri git callback
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Tam boyut + padding
        verticalArrangement = Arrangement.Center, // Dikey ortalama
        horizontalAlignment = Alignment.CenterHorizontally // Yatay ortalama
    ) {
        Text("Kategori: $categoryName", fontSize = 24.sp, fontWeight = FontWeight.Bold) // Başlık
        Spacer(modifier = Modifier.height(32.dp)) // Boşluk
        Button(onClick = { onNavigateToProduct("product123") }, modifier = Modifier.fillMaxWidth()) { // Ürün'e git
            Text("Ürün Detayı") // Buton metni
        }
        Spacer(modifier = Modifier.height(16.dp)) // Boşluk
        Button(onClick = onNavigateBack, modifier = Modifier.fillMaxWidth()) { // Geri git
            Text("Geri") // Buton metni
        }
    }
}

@Composable
fun ProductScreen(
    productId: String, // Ürün ID'si
    onNavigateBack: () -> Unit, // Geri git callback
    onAddToCart: (String) -> Unit // Sepete ekle callback
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Tam boyut + padding
        verticalArrangement = Arrangement.Center, // Dikey ortalama
        horizontalAlignment = Alignment.CenterHorizontally // Yatay ortalama
    ) {
        Text("Ürün: $productId", fontSize = 24.sp, fontWeight = FontWeight.Bold) // Başlık
        Spacer(modifier = Modifier.height(32.dp)) // Boşluk
        Button(onClick = { onAddToCart(productId) }, modifier = Modifier.fillMaxWidth()) { // Sepete ekle
            Text("Sepete Ekle") // Buton metni
        }
        Spacer(modifier = Modifier.height(16.dp)) // Boşluk
        Button(onClick = onNavigateBack, modifier = Modifier.fillMaxWidth()) { // Geri git
            Text("Geri") // Buton metni
        }
    }
}

@Composable
fun CartScreen(
    items: List<String>, // Sepet öğeleri
    onNavigateBack: () -> Unit // Geri git callback
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Tam boyut + padding
        verticalArrangement = Arrangement.Center, // Dikey ortalama
        horizontalAlignment = Alignment.CenterHorizontally // Yatay ortalama
    ) {
        Text("Sepetim", fontSize = 24.sp, fontWeight = FontWeight.Bold) // Başlık
        Spacer(modifier = Modifier.height(32.dp)) // Boşluk
        Text("Sepet öğeleri: ${items.size}") // Sepet öğe sayısı
        Spacer(modifier = Modifier.height(16.dp)) // Boşluk
        Button(onClick = onNavigateBack, modifier = Modifier.fillMaxWidth()) { // Geri git
            Text("Geri") // Buton metni
        }
    }
}

// ============================================================================
// 6. NAVIGATION TRICKS VE HIDDEN GEMS
// ============================================================================

@Composable
fun ConditionalNavigationExample() {
    var isLoggedIn by remember { mutableStateOf(false) } // Giriş durumu
    var currentScreen by remember { mutableStateOf(Screen.HOME) } // Mevcut ekran

    if (isLoggedIn) { // Giriş yapılmışsa
        when (currentScreen) { // Ekrana göre composable göster
            Screen.HOME -> HomeScreen(
                onNavigateToProfile = { currentScreen = Screen.PROFILE }, // Profil'e git
                onNavigateToSettings = { currentScreen = Screen.SETTINGS } // Ayarlar'a git
            )

            Screen.PROFILE -> ProfileScreen(
                onNavigateBack = { currentScreen = Screen.HOME } // Geri git
            )

            Screen.SETTINGS -> SettingsScreen(
                onNavigateBack = { currentScreen = Screen.HOME } // Geri git
            )
        }
    } else { // Giriş yapılmamışsa
        LoginScreen(
            onLoginSuccess = { isLoggedIn = true } // Giriş durumunu güncelle
        )
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) { // Giriş başarılı callback
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Tam boyut + padding
        verticalArrangement = Arrangement.Center, // Dikey ortalama
        horizontalAlignment = Alignment.CenterHorizontally // Yatay ortalama
    ) {
        Text("Giriş Yap", fontSize = 24.sp, fontWeight = FontWeight.Bold) // Başlık
        Spacer(modifier = Modifier.height(32.dp)) // Boşluk
        Button(onClick = onLoginSuccess, modifier = Modifier.fillMaxWidth()) { // Giriş başarılı
            Text("Giriş Yap") // Buton metni
        }
    }
}

@Composable
fun NavigationStateManagementExample() {
    var navigationState by remember { mutableStateOf("home") } // Navigation state

    LaunchedEffect(navigationState) { // Navigation state değiştiğinde
        // Burada navigation logic'i yapılabilir (analytics, logging, etc.)
    }

    Column(modifier = Modifier.padding(16.dp)) { // İç boşluk
        Text("Navigation State: $navigationState") // Mevcut state
        Spacer(modifier = Modifier.height(16.dp)) // Boşluk
        Button(onClick = { navigationState = "profile" }) { // Profil state'i
            Text("Profile State") // Buton metni
        }
        Spacer(modifier = Modifier.height(8.dp)) // Boşluk
        Button(onClick = { navigationState = "settings" }) { // Ayarlar state'i
            Text("Settings State") // Buton metni
        }
    }
}