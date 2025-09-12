package com.dilara.composeexample.architecture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * # CompositionLocal ile Yerel Kapsamlı Veri
 *
 * CompositionLocal, veriyi Composition boyunca örtülü olarak geçirmek için kullanılan bir araçtır.
 * Bu sayede sık kullanılan veriler (renkler, tip stilleri gibi) her composable'a parametre olarak
 * geçirilmek zorunda kalmaz.
 *
 * ## CompositionLocal Nedir?
 *
 * CompositionLocal, UI ağacında tree-scoped (ağaç kapsamlı) named objeler oluşturmanı sağlar.
 * Bu objeler, veriyi UI ağacı boyunca örtülü bir şekilde akıtır.
 *
 * ### Temel Kavramlar:
 * - **Composition**: Composable fonksiyonların çağrı grafiğinin kaydı
 * - **UI Tree/UI Hierarchy**: Composition süreci tarafından oluşturulan, güncellenen ve korunan LayoutNode ağacı
 * - **Tree-scoped**: Belirli bir UI ağacı bölümünde kapsamlı
 *
 * ## CompositionLocal Nasıl Çalışır?
 *
 * CompositionLocal öğeleri genellikle UI ağacının belirli bir düğümünde bir değerle sağlanır.
 * Bu değer, composable fonksiyonunda parametre olarak bildirilmeden, composable torunları tarafından kullanılabilir.
 *
 * ### Material Theme Örneği:
 * MaterialTheme, üç CompositionLocal instance sağlar:
 * - LocalColorScheme (renkler)
 * - LocalTypography (tipografi)
 * - LocalShapes (şekiller)
 *
 * ## CompositionLocal Kullanım Senaryoları
 *
 * ### ✅ İyi Kullanım Senaryoları:
 * - Tema bilgileri (renkler, tipografi)
 * - Android Context
 * - Lokalizasyon bilgileri
 * - Permission durumları
 * - Design system değerleri
 *
 * ### ❌ Kötü Kullanım Senaryoları:
 * - ViewModel referansları
 * - Screen-specific state
 * - Sadece birkaç composable tarafından kullanılan veriler
 * - Default değeri olmayan veriler
 *
 * ## CompositionLocal Oluşturma
 *
 * İki API vardır:
 * - **compositionLocalOf**: Recomposition sırasında sadece değeri okuyan içerik geçersiz kılınır
 * - **staticCompositionLocalOf**: Değer okumaları Compose tarafından takip edilmez, tüm content lambda yeniden compose edilir
 *
 * ## Alternatifler
 *
 * CompositionLocal her zaman en iyi çözüm değildir:
 * - **Explicit Parameters**: Composable'ın bağımlılıklarını açık hale getir
 * - **Inversion of Control**: Parent'ın logic'i execute etmesini sağla
 * - **Content Lambdas**: @Composable content lambda'ları kullan
 *
 * ## Best Practices
 * - Default değer sağla
 * - Tree-scoped veya sub-hierarchy-scoped kavramlar için kullan
 * - Aşırı kullanmaktan kaçın
 * - Debugging zorluğunu göz önünde bulundur
 * - IDE'de "Local" prefix kullan
 */

// ============================================================================
// 1. TEMEL COMPOSITIONLOCAL ÖRNEKLERİ
// ============================================================================

// --- Material Theme CompositionLocal Kullanımı ---
@Composable
fun MaterialThemeExample() {
    MaterialTheme { // Material tema sağlayıcısı
        Column {
            Text(
                text = "Material Theme Renkleri",
                color = MaterialTheme.colorScheme.primary // Tema renklerini kullan
            )

            Text(
                text = "Typography Stili",
                style = MaterialTheme.typography.headlineLarge // Tema tipografisini kullan
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant // Tema yüzey rengi
                )
            ) {
                Text(
                    text = "Card içeriği",
                    modifier = Modifier.padding(16.dp) // İç boşluk ekle
                )
            }
        }
    }
}

// --- LocalContext Kullanımı ---
@Composable
fun ContextExample() {
    val context = LocalContext.current // Android context'ini al

    Column {
        Text("Context: ${context.packageName}") // Paket adını göster
        Text("Resources: ${context.resources.displayMetrics.density}") // Ekran yoğunluğunu göster
    }
}

// --- LocalContentColor Kullanımı ---
@Composable
fun ContentColorExample() {
    MaterialTheme {
        Surface {
            Column {
                Text("Surface'in default content color'ı") // Varsayılan renk

                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.primary // Primary rengi sağla
                ) {
                    Text("Primary color ile yazı") // Primary renk kullan
                    Text("Bu da primary color kullanır") // Aynı renk devam eder

                    CompositionLocalProvider(
                        LocalContentColor provides MaterialTheme.colorScheme.error // Error rengi sağla
                    ) {
                        Text("Error color ile yazı") // Error renk kullan
                    }
                }
            }
        }
    }
}

// ============================================================================
// 2. ÖZEL COMPOSITIONLOCAL OLUŞTURMA
// ============================================================================

// --- Elevation CompositionLocal ---
data class Elevations(
    val card: Dp = 0.dp, // Card yüksekliği
    val default: Dp = 0.dp, // Varsayılan yükseklik
    val floating: Dp = 0.dp // Floating element yüksekliği
)

val LocalElevations = compositionLocalOf { Elevations() } // Elevation CompositionLocal oluştur

@Composable
fun ElevationExample() {
    val elevations = Elevations(
        card = 4.dp, // Card yüksekliği
        default = 2.dp, // Varsayılan yükseklik
        floating = 8.dp // Floating yükseklik
    )

    CompositionLocalProvider(LocalElevations provides elevations) { // Elevation değerlerini sağla
        Column {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = LocalElevations.current.card // CompositionLocal'dan card yüksekliği al
                )
            ) {
                Text("Card elevation: ${LocalElevations.current.card}") // Yükseklik değerini göster
            }

            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary, // Primary renk
                onClick = { } // Tıklama olayı
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add") // Plus ikonu
            }
        }
    }
}

// --- Theme Mode CompositionLocal ---
data class AppTheme(
    val isDark: Boolean, // Karanlık tema mı?
    val primaryColor: Color, // Ana renk
    val backgroundColor: Color // Arka plan rengi
)

val LocalAppTheme = compositionLocalOf { // Tema CompositionLocal oluştur
    AppTheme(
        isDark = false, // Varsayılan açık tema
        primaryColor = Color.Blue, // Varsayılan mavi renk
        backgroundColor = Color.White // Varsayılan beyaz arka plan
    )
}

@Composable
fun CustomThemeExample() {
    val lightTheme = AppTheme(
        isDark = false, // Açık tema
        primaryColor = Color.Blue, // Mavi ana renk
        backgroundColor = Color.White // Beyaz arka plan
    )

    val darkTheme = AppTheme(
        isDark = true, // Karanlık tema
        primaryColor = Color.Cyan, // Cyan ana renk
        backgroundColor = Color.Black // Siyah arka plan
    )

    CompositionLocalProvider(LocalAppTheme provides lightTheme) { // Açık tema sağla
        Column(
            modifier = Modifier
                .fillMaxSize() // Tam ekran
                .background(LocalAppTheme.current.backgroundColor) // Tema arka plan rengi
        ) {
            Text(
                text = "Light Theme",
                color = LocalAppTheme.current.primaryColor, // Tema ana rengi
                fontSize = 24.sp, // Büyük font
                fontWeight = FontWeight.Bold // Kalın yazı
            )

            CompositionLocalProvider(LocalAppTheme provides darkTheme) { // Karanlık tema sağla
                Text(
                    text = "Dark Theme",
                    color = LocalAppTheme.current.primaryColor, // Tema ana rengi
                    fontSize = 24.sp, // Büyük font
                    fontWeight = FontWeight.Bold // Kalın yazı
                )
            }
        }
    }
}

// ============================================================================
// 3. STATIC COMPOSITIONLOCAL ÖRNEKLERİ
// ============================================================================

// --- Static CompositionLocal (Performans için) ---
data class AppConfig(
    val apiUrl: String, // API URL'si
    val version: String, // Uygulama versiyonu
    val debugMode: Boolean // Debug modu açık mı?
)

val LocalAppConfig = staticCompositionLocalOf { // Static CompositionLocal (performanslı)
    AppConfig(
        apiUrl = "https://api.example.com", // Varsayılan API URL
        version = "1.0.0", // Varsayılan versiyon
        debugMode = false // Varsayılan debug kapalı
    )
}

@Composable
fun StaticCompositionLocalExample() {
    val config = AppConfig(
        apiUrl = "https://production.api.com", // Production API URL
        version = "2.0.0", // Production versiyon
        debugMode = true // Debug modu açık
    )

    CompositionLocalProvider(LocalAppConfig provides config) { // Config değerlerini sağla
        Column {
            Text("API URL: ${LocalAppConfig.current.apiUrl}") // API URL'yi göster
            Text("Version: ${LocalAppConfig.current.version}") // Versiyonu göster
            Text("Debug Mode: ${LocalAppConfig.current.debugMode}") // Debug durumunu göster
        }
    }
}

// ============================================================================
// 4. İLERİ SEVİYE COMPOSITIONLOCAL ÖRNEKLERİ
// ============================================================================

// --- Permission State CompositionLocal ---
data class PermissionState(
    val cameraPermission: Boolean = false, // Kamera izni var mı?
    val locationPermission: Boolean = false, // Konum izni var mı?
    val storagePermission: Boolean = false // Depolama izni var mı?
)

val LocalPermissionState = compositionLocalOf { PermissionState() } // İzin durumu CompositionLocal

@Composable
fun PermissionExample() {
    val permissions = PermissionState(
        cameraPermission = true, // Kamera izni var
        locationPermission = false, // Konum izni yok
        storagePermission = true // Depolama izni var
    )

    CompositionLocalProvider(LocalPermissionState provides permissions) { // İzin durumlarını sağla
        Column {
            if (LocalPermissionState.current.cameraPermission) { // Kamera izni varsa
                Button(onClick = { }) {
                    Text("Kamera Aç") // Kamera butonu göster
                }
            }

            if (LocalPermissionState.current.locationPermission) { // Konum izni varsa
                Button(onClick = { }) {
                    Text("Konum Al") // Konum butonu göster
                }
            }

            if (LocalPermissionState.current.storagePermission) { // Depolama izni varsa
                Button(onClick = { }) {
                    Text("Dosya Kaydet") // Dosya kaydet butonu göster
                }
            }
        }
    }
}

// --- User Session CompositionLocal ---
data class UserSession(
    val userId: String, // Kullanıcı ID'si
    val userName: String, // Kullanıcı adı
    val isLoggedIn: Boolean, // Giriş yapılmış mı?
    val userRole: String // Kullanıcı rolü
)

val LocalUserSession = compositionLocalOf { // Kullanıcı oturumu CompositionLocal
    UserSession(
        userId = "", // Boş kullanıcı ID
        userName = "Guest", // Misafir kullanıcı
        isLoggedIn = false, // Giriş yapılmamış
        userRole = "guest" // Misafir rolü
    )
}

@Composable
fun UserSessionExample() {
    val userSession = UserSession(
        userId = "123", // Kullanıcı ID
        userName = "Dilara", // Kullanıcı adı
        isLoggedIn = true, // Giriş yapılmış
        userRole = "admin" // Admin rolü
    )

    CompositionLocalProvider(LocalUserSession provides userSession) { // Kullanıcı oturumunu sağla
        Column {
            Text("Hoş geldin, ${LocalUserSession.current.userName}!") // Hoş geldin mesajı

            if (LocalUserSession.current.isLoggedIn) { // Giriş yapılmışsa
                Text("Giriş yapılmış") // Giriş durumu

                if (LocalUserSession.current.userRole == "admin") { // Admin rolü varsa
                    Button(onClick = { }) {
                        Text("Admin Paneli") // Admin paneli butonu
                    }
                }
            }
        }
    }
}

// ============================================================================
// 5. COMPOSITIONLOCAL VS EXPLICIT PARAMETERS
// ============================================================================

// --- Kötü Örnek: ViewModel'i CompositionLocal ile geçirme ---
class BadViewModel {
    fun loadData() {} // Veri yükle
    fun saveData() {} // Veri kaydet
}

val LocalBadViewModel = compositionLocalOf<BadViewModel?> { null } // ViewModel CompositionLocal (kötü örnek)

@Composable
fun BadCompositionLocalExample() {
    val viewModel = BadViewModel() // ViewModel oluştur

    CompositionLocalProvider(LocalBadViewModel provides viewModel) { // ViewModel'i sağla (kötü örnek)
        Column {
            BadChildComponent() // Child component çağır
        }
    }
}

@Composable
fun BadChildComponent() {
    val viewModel = LocalBadViewModel.current // ViewModel'i al (kötü örnek)
    viewModel?.let { // ViewModel varsa
        Button(onClick = { it.loadData() }) { // Veri yükle butonu
            Text("Veri Yükle")
        }
    }
}

// --- İyi Örnek: Explicit parameters ---
@Composable
fun GoodExplicitExample() {
    val viewModel = BadViewModel() // ViewModel oluştur

    GoodChildComponent(
        onLoadData = { viewModel.loadData() }, // Veri yükle fonksiyonu
        onSaveData = { viewModel.saveData() } // Veri kaydet fonksiyonu
    )
}

@Composable
fun GoodChildComponent(
    onLoadData: () -> Unit, // Veri yükle callback
    onSaveData: () -> Unit // Veri kaydet callback
) {
    Column {
        Button(onClick = onLoadData) { // Veri yükle butonu
            Text("Veri Yükle")
        }
        Button(onClick = onSaveData) { // Veri kaydet butonu
            Text("Veri Kaydet")
        }
    }
}

// ============================================================================
// 6. INVERSION OF CONTROL ÖRNEKLERİ
// ============================================================================

// --- Content Lambda ile Inversion of Control ---
@Composable
fun InversionOfControlExample() {
    val viewModel = BadViewModel() // ViewModel oluştur

    ReusableScreen(
        content = { // Content lambda ile kontrolü parent'a ver
            Button(onClick = { viewModel.loadData() }) { // Veri yükle butonu
                Text("Veri Yükle")
            }
        }
    )
}

@Composable
fun ReusableScreen(
    content: @Composable () -> Unit // Content lambda parametresi
) {
    Column {
        Text("Reusable Screen Header") // Header
        content() // Content'i çağır
        Text("Reusable Screen Footer") // Footer
    }
}

// --- Event Handler ile Inversion of Control ---
@Composable
fun EventHandlerExample() {
    val viewModel = BadViewModel() // ViewModel oluştur

    ReusableButton(
        text = "Veri Yükle", // Buton metni
        onClick = { viewModel.loadData() } // Tıklama olayı
    )
}

@Composable
fun ReusableButton(
    text: String, // Buton metni
    onClick: () -> Unit // Tıklama callback
) {
    Button(onClick = onClick) { // Tıklama olayını dinle
        Text(text) // Metni göster
    }
}

// ============================================================================
// 7. GERÇEK DÜNYA ÖRNEKLERİ
// ============================================================================

// --- Chat Uygulaması ---
data class ChatContext(
    val currentChannel: String = "", // Mevcut kanal
    val isOnline: Boolean = false, // Çevrimiçi mi?
    val unreadCount: Int = 0 // Okunmamış mesaj sayısı
)

val LocalChatContext = compositionLocalOf { ChatContext() } // Chat context CompositionLocal

@Composable
fun ChatExample() {
    val chatContext = ChatContext(
        currentChannel = "Genel", // Genel kanal
        isOnline = true, // Çevrimiçi
        unreadCount = 5 // 5 okunmamış mesaj
    )

    CompositionLocalProvider(LocalChatContext provides chatContext) { // Chat context'i sağla
        Column {
            Text("Kanal: ${LocalChatContext.current.currentChannel}") // Kanal adı
            Text("Durum: ${if (LocalChatContext.current.isOnline) "Çevrimiçi" else "Çevrimdışı"}") // Çevrimiçi durumu
            Text("Okunmamış: ${LocalChatContext.current.unreadCount}") // Okunmamış mesaj sayısı
        }
    }
}

// ============================================================================
// 8. COMPOSITIONLOCAL TEST ÖRNEKLERİ
// ============================================================================

// --- Test için CompositionLocal Provider ---
@Composable
fun TestCompositionLocalExample() {
    val testTheme = AppTheme(
        isDark = true, // Karanlık tema
        primaryColor = Color.Red, // Kırmızı ana renk
        backgroundColor = Color.Gray // Gri arka plan
    )

    CompositionLocalProvider(LocalAppTheme provides testTheme) { // Test temasını sağla
        Text(
            text = "Test Theme",
            color = LocalAppTheme.current.primaryColor // Test teması rengi
        )
    }
}

// --- Preview için CompositionLocal ---
@Composable
fun PreviewCompositionLocalExample() {
    val previewConfig = AppConfig(
        apiUrl = "https://preview.api.com", // Preview API URL
        version = "preview", // Preview versiyon
        debugMode = true // Debug modu açık
    )

    CompositionLocalProvider(LocalAppConfig provides previewConfig) { // Preview config'i sağla
        Column {
            Text("Preview Mode") // Preview modu
            Text("API: ${LocalAppConfig.current.apiUrl}") // API URL'yi göster
        }
    }
}

// ============================================================================
// 9. COMPOSITIONLOCAL BEST PRACTICES
// ============================================================================

// --- Default değer ile güvenli CompositionLocal ---
val LocalSafeConfig = compositionLocalOf { // Güvenli CompositionLocal
    AppConfig(
        apiUrl = "https://default.api.com", // Varsayılan API URL
        version = "1.0.0", // Varsayılan versiyon
        debugMode = false // Varsayılan debug kapalı
    )
}

@Composable
fun SafeCompositionLocalExample() {
    Column {
        Text("API URL: ${LocalSafeConfig.current.apiUrl}") // API URL'yi göster
        Text("Version: ${LocalSafeConfig.current.version}") // Versiyonu göster
        Text("Debug: ${LocalSafeConfig.current.debugMode}") // Debug durumunu göster
    }
}

// --- Conditional CompositionLocal ---
@Composable
fun ConditionalCompositionLocalExample(isDarkMode: Boolean) {
    val theme = if (isDarkMode) { // Karanlık mod mu?
        AppTheme(
            isDark = true, // Karanlık tema
            primaryColor = Color.Cyan, // Cyan ana renk
            backgroundColor = Color.Black // Siyah arka plan
        )
    } else { // Açık mod
        AppTheme(
            isDark = false, // Açık tema
            primaryColor = Color.Blue, // Mavi ana renk
            backgroundColor = Color.White // Beyaz arka plan
        )
    }

    CompositionLocalProvider(LocalAppTheme provides theme) { // Tema sağla
        Text(
            text = "Theme: ${if (LocalAppTheme.current.isDark) "Dark" else "Light"}", // Tema durumu
            color = LocalAppTheme.current.primaryColor // Tema rengi
        )
    }
}

// ============================================================================
// 10. COMPOSITIONLOCAL TRICKS VE HIDDEN GEMS
// ============================================================================

// --- Nested CompositionLocal Override ---
@Composable
fun NestedOverrideExample() {
    val parentTheme = AppTheme(
        isDark = false, // Açık tema
        primaryColor = Color.Blue, // Mavi ana renk
        backgroundColor = Color.White // Beyaz arka plan
    )

    CompositionLocalProvider(LocalAppTheme provides parentTheme) { // Parent tema sağla
        Text("Parent Theme: ${LocalAppTheme.current.primaryColor}") // Parent tema rengi

        val childTheme = AppTheme(
            isDark = true, // Karanlık tema
            primaryColor = Color.Red, // Kırmızı ana renk
            backgroundColor = Color.Black // Siyah arka plan
        )

        CompositionLocalProvider(LocalAppTheme provides childTheme) { // Child tema sağla
            Text("Child Theme: ${LocalAppTheme.current.primaryColor}") // Child tema rengi

            val grandchildTheme = AppTheme(
                isDark = false, // Açık tema
                primaryColor = Color.Green, // Yeşil ana renk
                backgroundColor = Color.Yellow // Sarı arka plan
            )

            CompositionLocalProvider(LocalAppTheme provides grandchildTheme) { // Grandchild tema sağla
                Text("Grandchild Theme: ${LocalAppTheme.current.primaryColor}") // Grandchild tema rengi
            }
        }
    }
}

// --- CompositionLocal ile State Management ---
data class AppState(
    val isLoading: Boolean = false, // Yükleniyor mu?
    val error: String? = null, // Hata var mı?
    val data: List<String> = emptyList() // Veri listesi
)

val LocalAppState = compositionLocalOf { AppState() } // App state CompositionLocal

@Composable
fun CompositionLocalStateManagementExample() {
    var appState by remember { mutableStateOf(AppState()) } // App state'i hatırla

    CompositionLocalProvider(LocalAppState provides appState) { // App state'i sağla
        Column {
            Text("Loading: ${LocalAppState.current.isLoading}") // Yükleme durumu
            Text("Error: ${LocalAppState.current.error ?: "None"}") // Hata durumu
            Text("Data Count: ${LocalAppState.current.data.size}") // Veri sayısı

            Button(
                onClick = { // Tıklama olayı
                    appState = appState.copy(isLoading = !appState.isLoading) // Yükleme durumunu değiştir
                }
            ) {
                Text("Toggle Loading") // Yükleme durumunu değiştir butonu
            }
        }
    }
}

// --- CompositionLocal ile Dependency Injection ---
interface ApiService {
    fun fetchData(): String // Veri getir
}

class RealApiService : ApiService {
    override fun fetchData(): String = "Real Data" // Gerçek veri
}

class MockApiService : ApiService {
    override fun fetchData(): String = "Mock Data" // Mock veri
}

val LocalApiService = compositionLocalOf<ApiService> { MockApiService() } // API service CompositionLocal

@Composable
fun DependencyInjectionExample() {
    val apiService = RealApiService() // Gerçek API service oluştur

    CompositionLocalProvider(LocalApiService provides apiService) { // API service'i sağla
        Column {
            Text("API Service: ${LocalApiService.current.fetchData()}") // API service verisini göster
        }
    }
}
