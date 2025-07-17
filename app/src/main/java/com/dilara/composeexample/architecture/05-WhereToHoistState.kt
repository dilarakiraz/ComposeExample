package com.dilara.composeexample.architecture

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch

/**
 * # State Nereye Hoist Edilmeli?
 *
 * Jetpack Compose'da state'in nereye taşınacağı, UI mantığı mı yoksa iş mantığı mı gerektiriyor buna göre değişir.
 *
 * ## En İyi Pratik
 * - State'i, onu okuyan ve değiştiren tüm composable'ların en düşük ortak parent'ına taşı.
 * - State'i, kullanıldığı yere en yakın yerde tut.
 * - State sahibi, dışarıya sadece immutable state ve event fonksiyonları açmalı.
 * - Eğer iş mantığı gerekiyorsa, state'i ViewModel'e taşı (bu durumda state Composition dışında tutulur).
 *
 * ## UI State Türleri
 * - **Ekran UI State'i:** Ekranda gösterilecek tüm veriler (ör: haber listesi, form verisi). Genellikle app data ile bağlantılıdır.
 * - **UI Element State'i:** Sadece bir UI elementine ait, ör: bir butonun seçili olup olmaması, bir kartın açık/kapalı olması.
 *
 * ## Logic Türleri
 * - **Business Logic:** Uygulamanın iş kuralları (ör: favori ekleme, veri kaydetme, API çağrısı). Genellikle domain/data katmanında olur.
 * - **UI Logic:** Sadece UI'ya özel mantık (ör: scroll, animasyon, geçici seçim, arama kutusu ipucu).
 *
 * ## State Hoisting Senaryoları
 * - Sadece bir composable kullanıyorsa, içeride tut.
 * - Birden fazla composable kullanıyorsa, ortak parent'a taşı.
 * - Karmaşık UI logic için state holder class (veya ViewModel) kullan.
 * - Business logic gerekiyorsa, ViewModel'e taşı. (State Composition dışında tutulur.)
 *
 * ## State'i Hoist Etmeye Gerek Olmayan Durumlar
 * - State sadece bir composable tarafından okunup yazılıyorsa, içeride tutmak daha basit ve performanslıdır.
 * - Örnek: Sadece kendi açılıp kapanan bir kart, animasyon state'i.
 *
 * ## State'i Hoist Etmek Gereken Durumlar
 * - Birden fazla composable aynı state'i okuyup yazıyorsa (örn. liste scroll state'i, seçim durumu), state'i ortak parent'a taşı.
 * - Böylece kod daha okunur, test edilebilir ve tekrar kullanılabilir olur.
 *
 * ## State Holder Sınıfları
 * - Karmaşık UI logic veya birden fazla state alanı için, state holder class (veya ViewModel) kullanmak kodun sorumluluklarını ayırır.
 * - State holder class'lar, UI logic'i ve state'i yönetir, composable'lar ise sadece UI üretir.
 *
 * ## ViewModel ile State Hoisting
 * - Ekranlar arası veya iş mantığı gerektiren state için ViewModel kullanılır.
 * - ViewModel, business logic'i ve ekran state'ini yönetir, UI'ya immutable state ve event fonksiyonları açar.
 * - ViewModel, Composition dışında tutulur ve Activity/Fragment/Navigasyon scope'u ile yaşar.
 *
 * ## Property Drilling
 * - State ve event'leri, sadece ihtiyaç duyan composable'lara parametre olarak iletmek en iyi pratiktir.
 * - Wrapper class ile tüm state/event'leri tek objede toplamak yerine, fonksiyon imzasında açıkça göstermek daha okunur ve test edilebilirdir.
 * - Bu, fonksiyon imzalarını uzatabilir ama composable'ın sorumluluğu net olur.
 *
 * ## Uyarı
 * - Bazı Compose state objeleri (örn. DrawerState, LazyListState) suspend fonksiyonlar içerir. Bunları sadece Composition'a bağlı bir CoroutineScope ile çağır.
 * - ViewModelScope ile çağırırsan animasyonlar çalışmaz ve hata alırsın (örn. MonotonicFrameClock hatası).
 * - Çözüm: UI'dan ViewModel'e Composition scope'u (örn. rememberCoroutineScope()) parametre olarak ilet.
 *
 * ## Özet
 * - State'i en yakın ortak parent'ta tut.
 * - Sadece ihtiyaç duyanlara parametre ilet.
 * - İş mantığı için ViewModel veya state holder class kullan.
 * - Global state, gereksiz hoisting ve gereksiz recomposition'dan kaçın.
 * - rememberSaveable, derivedStateOf, remember(key) gibi Compose araçlarını kullan.
 *
 * ---
 */

// --- 1. Sadece bir composable kullanıyorsa, içeride tut ---
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) } // Sadece burada kullanılıyor
    Button(onClick = { count++ }) { Text("$count") }
}

// --- 2. Birden fazla composable kullanıyorsa, parent'a taşı (hoist) ---
@Composable
fun ParentCounter() {
    var count by remember { mutableStateOf(0) } // Ortak parent
    Row {
        Button(onClick = { count++ }) { Text("Artır") }
        Text("Değer: $count")
    }
}

// --- 3. State'i paylaşmak ---
@Composable
fun ListScreen() { //Birden fazla composable aynı state'i kullanıyorsa, state'i ortak parent'a taşımalısın.
    var selected by remember { mutableStateOf(-1) }
    Column {
        ItemList(selected, onSelect = { selected = it })
        Text("Seçili: $selected")
    }
}

@Composable
fun ItemList(selected: Int, onSelect: (Int) -> Unit) {
    Column {
        repeat(3) { i ->
            Button(
                onClick = { onSelect(i) },
                colors = if (selected == i) ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary) else ButtonDefaults.buttonColors()
            ) { Text("Item $i") }
        }
    }
}
//Eğer selected state'ini ItemList içinde tutsaydık, parent olan ListScreen seçili item'ı bilemezdi.
//Eğer hem parent hem child kendi içinde ayrı ayrı state tutsaydı, tek bir gerçek (source of truth) olmazdı ve UI tutarsız olurdu.
//Bu yüzden, state'i en yakın ortak parent'a taşıyoruz (yani ListScreen), ve ihtiyacı olan child'lara parametre olarak iletiyoruz.


// --- 4. rememberSaveable: Ekran döndürmede kaybolmasın ---
@Composable
fun SaveableExample() {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("Ad") })
}

// --- 5. derivedStateOf: Hesaplanan state, performans için ---
@Composable
fun DerivedStateSample() {
    var text by remember { mutableStateOf("") }
    val isLong by remember(text) { derivedStateOf { text.length > 5 } }
    Column {
        OutlinedTextField(value = text, onValueChange = { text = it })
        if (isLong) Text("Uzun")
    }
}

// --- 6. Liste scroll state paylaşımı (LazyListState) ---
@Composable
fun ScrollExample() {
    val listState = rememberLazyListState() // Parent'ta tanımla, paylaş
    val scope = rememberCoroutineScope()
    Column {
        LazyColumn(state = listState, modifier = Modifier.height(120.dp)) {
            items(20) { Text("Satır $it", Modifier.padding(8.dp)) }
        }
        Button(onClick = {
            scope.launch {
                listState.animateScrollToItem(19)
            }
        }) { Text("En Alta Git") }
    }
}

// --- 7. State Holder: Karmaşık state için class kullan ---
class FormStateHoist {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    fun clear() {
        name = ""; email = ""
    }
}

@Composable
fun FormScreenHoistExample() {
    val state = remember { FormStateHoist() }
    Column {
        OutlinedTextField(value = state.name, onValueChange = { state.name = it }, label = { Text("Ad") })
        OutlinedTextField(value = state.email, onValueChange = { state.email = it }, label = { Text("Mail") })
        Button(onClick = { state.clear() }) { Text("Temizle") }
    }
}

// --- 8. ViewModel---
class MyViewModel {
    var count by mutableStateOf(0)
        private set

    fun inc() {
        count++
    }
}

@Composable
fun ViewModelScreen(vm: MyViewModel = remember { MyViewModel() }) {
    Column {
        Text("VM Count: ${vm.count}")
        Button(onClick = { vm.inc() }) { Text("Arttır") }
    }
}

// --- 9. remember(key): Input değişince state sıfırlama ---
@Composable
fun Avatar(avatarId: Int) {
    val brush = remember(avatarId) { "Brush $avatarId" } // Id değişirse brush sıfırlanır
    Text(brush)
}

// --- 10. Anti-pattern: Global state kullanma! ---
object GlobalStateHoist {
    var count = 0
}

@Composable
fun GlobalStateExampleHoist() {
    Button(onClick = { GlobalStateHoist.count++ }) {
        Text("Global: ${GlobalStateHoist.count}") // UI güncellenmez!
    }
}

// --- 11. Property Drilling: Sadece ihtiyaç duyanlara parametre ilet ---
@Composable
fun DrillParent() {
    var value by remember { mutableStateOf(0) }
    DrillChild(value, onInc = { value++ })
}

@Composable
fun DrillChild(value: Int, onInc: () -> Unit) {
    Button(onClick = onInc) { Text("$value") }
}

// --- 12. Suspend fonksiyonlar: UI scope kullan! ---
class DrawerState {
    suspend fun close() {}
}

class DrawerVM {
    val drawer = DrawerState()
}

@Composable
fun DrawerScreen(vm: DrawerVM = remember { DrawerVM() }) {
    val scope = rememberCoroutineScope()
    Button(onClick = { scope.launch { vm.drawer.close() } }) { Text("Kapat") }
} 