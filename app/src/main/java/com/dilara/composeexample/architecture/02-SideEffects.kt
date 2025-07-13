package com.dilara.composeexample.architecture

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * =============================================================================
 * ⭐️ Jetpack Compose'da Side Effect (Yan Etki) Yönetimi
 * =============================================================================
 *
 * Side effect, bir composable fonksiyonun dışında uygulama durumunu değiştiren işlemdir.
 * Compose'da recomposition (yeniden çizim) beklenmedik şekilde ve sırayla olabileceği için, composable'lar mümkün olduğunca side effect'ten arındırılmalıdır.
 * Ancak, bazen Snackbar göstermek, animasyon başlatmak, navigasyon yapmak gibi side effect'ler gereklidir.
 */

/**
 *  LaunchedEffect: Composable'ın ömrüne bağlı coroutine başlatma
 * -----------------------------------------------------------------------------
 * - State veya parametre değiştiğinde bir defa çalışan side effect başlatır.
 * - Genellikle animasyon, API çağrısı, zamanlayıcı gibi işlemler için kullanılır.
 *
 * Örnek: Nabız animasyonu. Her pulseRateMs değişiminde animasyon tekrar başlar.
 */
@Composable
fun PulseAnimationExample(pulseRateMs: Long = 1000L) {
    // Animatable ile alpha değerini animasyonlu olarak değiştiriyoruz.
    val alpha = remember { Animatable(1f) }
    // LaunchedEffect, pulseRateMs değiştikçe veya composable ekrana geldikçe çalışır.
    LaunchedEffect(pulseRateMs) {
        while (isActive) {
            delay(pulseRateMs)
            alpha.animateTo(0.3f)
            alpha.animateTo(1f)
        }
    }
    Box(
        Modifier
            .size(80.dp)
            .graphicsLayer { this.alpha = alpha.value }
            .background(MaterialTheme.colorScheme.primary)
    )
}

// -----------------------------------------------------------------------------
/**
 *  rememberCoroutineScope: Event handler'da coroutine başlatma
 * -----------------------------------------------------------------------------
 * - Kullanıcı etkileşimiyle (örn. buton tıklama) coroutine başlatmak için kullanılır.
 * - Scope, composable'ın ömrüne bağlıdır.
 *
 * Örnek: Butona tıklanınca Snackbar göster.
 */
@Composable
fun SnackbarOnButtonClickExample(snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()
    Button(onClick = {
        // Doğru: Coroutine'i event handler içinde başlatıyoruz.
        scope.launch {
            snackbarHostState.showSnackbar("Butona tıklandı!")
        }
    }) {
        Text("Snackbar Göster")
    }
}

// -----------------------------------------------------------------------------
/**
 *  rememberUpdatedState: Effect içinde güncel değeri referansla
 * -----------------------------------------------------------------------------
 * - Effect'in yeniden başlatılmasını istemediğiniz, ama güncel değeri kullanmak istediğinizde kullanılır.
 *
 * Örnek: Ekran 2 saniye sonra kapanacak, onTimeout fonksiyonu her zaman güncel olmalı.
 */
@Composable
fun LandingScreenWithTimeout(onTimeout: () -> Unit) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    // LaunchedEffect(true): Sadece bir defa çalışır, recomposition'da tekrar başlamaz.
    LaunchedEffect(true) {
        delay(2000)
        currentOnTimeout()
    }
    Text("Hoş geldin! Bu ekran birazdan kapanacak.")
}

// -----------------------------------------------------------------------------
/**
 *  DisposableEffect: Temizlik gerektiren side effect'ler
 * -----------------------------------------------------------------------------
 * - Listener, observer gibi kaynakları ekleyip kaldırmak için kullanılır.
 * - Key değişirse veya composable ekrandan kalkarsa side effect temizlenir.
 *
 * Örnek: Lifecycle event'lerini dinle, ekrandan kalkınca observer'ı kaldır.
 */
/**
 * Bu composable, ekrana geldiğinde bir lifecycle observer ekler ve çıktığında kaldırır.
 * onStart ve onStop parametreleriyle, ilgili lifecycle event'lerinde istediğin işlemi tetikleyebilirsin.
 * Özellikle analytics, log, kaynak yönetimi gibi işlemler için idealdir.
 */
@Composable
fun LifecycleObserverExample(
    onStart: () -> Unit,
    onStop: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current // Şu anki lifecycle owner alınır
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)

    DisposableEffect(lifecycleOwner) { // Composable ekrana gelince başlar, çıkınca temizlenir
        val observer = LifecycleEventObserver { _, event -> // Lifecycle event'lerini dinleyen observer
            when (event) {
                Lifecycle.Event.ON_START -> currentOnStart() // Ekran açıldığında tetiklenir
                Lifecycle.Event.ON_STOP -> currentOnStop()   // Ekran kapandığında tetiklenir
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer) // Observer eklenir
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer) // Composable çıkınca observer kaldırılır
        }
    }
    Text("Lifecycle event'leri logcat'te gözlemlenebilir.")
}

// -----------------------------------------------------------------------------
/**
 *  SideEffect: Compose dışı objeleri güncelle
 * -----------------------------------------------------------------------------
 * - Her başarılı recomposition sonrası çalışır.
 * - Analytics, log, dış kütüphane güncellemesi gibi işlemler için kullanılır.
 *
 * Örnek: Kullanıcı tipi değiştikçe analytics servisine bildir.
 */
@Composable
fun AnalyticsSideEffectExample(userType: String) {
    SideEffect {
        // Her recomposition sonrası analytics güncellenir.
        println("Analytics güncellendi: $userType")
    }
    Text("Kullanıcı tipi: $userType")
}

// -----------------------------------------------------------------------------
/**
 *  produceState: Compose dışı state'i Compose'a çevir
 * -----------------------------------------------------------------------------
 * - Harici veri kaynaklarını (Flow, LiveData, repository) Compose state'ine dönüştürmek için kullanılır.
 *
 * Örnek: Ağdan resim yükle, yüklenme durumunu Compose state olarak döndür.
 */
@Composable
fun LoadNetworkImageExample(url: String, imageRepository: ImageRepository): State<Result<Image>> {
    return produceState<Result<Image>>(initialValue = Result.Loading, url, imageRepository) {
        val image = imageRepository.load(url)
        value = if (image == null) Result.Error else Result.Success(image)
    }
}

// -----------------------------------------------------------------------------
/**
 *  derivedStateOf: State'ten verimli türetilmiş state oluştur
 * -----------------------------------------------------------------------------
 * - Sadece türetilmiş değeri önemsediğinizde ve gereksiz recomposition'ı önlemek istediğinizde kullanılır.
 *
 * Örnek: Kullanıcı listeyi kaydırınca "En Üste Git" butonunu göster.
 */
@Composable
fun ScrollToTopButtonExample(messages: List<String>) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        items(messages.size) { index ->
            Text(messages[index])
        }
    }
    // derivedStateOf ile gereksiz recomposition önlenir.
    val showButton by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    if (showButton) {
        Button(onClick = { /* en üste kaydır */ }) {
            Text("En Üste Git")
        }
    }
}

/**
 * Kullanıcı listeyi kaydırıp ilk item'ı geçtiğinde bir analytics/log tetiklemek için kullanılır.
 * snapshotFlow ile Compose state'i Flow'a çevrilir ve scroll takibi yapılır.
 */
@Composable
fun ScrollAnalyticsExample() {
    val listState = rememberLazyListState() // Liste scroll durumunu tutar
    LaunchedEffect(listState) { // listState değişirse effect yeniden başlar
        snapshotFlow { listState.firstVisibleItemIndex } // Scroll index değişimini Flow olarak gözlemler
            .map { it > 0 } // İlk item'ı geçti mi?
            .distinctUntilChanged() // Sadece değişiklikte tetiklenir
            .filter { it } // Sadece true olduğunda devam et
            .collect {
                println("Kullanıcı ilk item'ı geçti!")
            }
    }
    LazyColumn(state = listState) {
        items(100) { Text("Item $it") }
    }
}


/**
 * -----------------------------------------------------------------------------
 * - LaunchedEffect ile navigation, Snackbar gösterme, animasyon tetikleme gibi işlemler yapılır.
 * - DisposableEffect ile listener, sensor, broadcast receiver ekleyip kaldırmak için kullanılır.
 * - SideEffect ile analytics, log gibi dış kütüphaneleri güncellemek için kullanılır.
 * - produceState ile Flow, LiveData gibi harici veri kaynakları Compose'a entegre edilir.
 * - snapshotFlow ile gelişmiş state gözlemi ve analytics yapılabilir.
 * - rememberCoroutineScope, kullanıcı etkileşimli işlemler için idealdir.
 * - onDispose ile mutlaka temizlik yapın, aksi halde memory leak oluşabilir.
 * - Effect key'lerinizi test edin: değiştirince effect beklediğiniz gibi restart oluyor mu?
 * - Compose'da side effect'ler, UI'nın reaktif ve güvenli kalmasını sağlar. Yanlış kullanımı, performans ve hata riskini artırır.
 */

// -----------------------------------------------------------------------------
class Image
class ImageRepository {
    suspend fun load(url: String): Image? = null
}

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    object Error : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
}
