package com.dilara.composeexample.architecture

import android.os.Parcelable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.parcelize.Parcelize

/**
 * # State Management in Jetpack Compose
 *
 * ## State Nedir?
 * State, zamanla değişebilen ve UI'da gösterilen her türlü veridir. Örneğin: kullanıcı girişi, animasyon durumu, veri yükleniyor mu gibi.
 *
 * ## State ve Composition
 * Compose deklaratif bir UI sistemidir. UI'yi güncellemenin tek yolu, aynı composable'ı yeni argümanlarla çağırmaktır. State değişirse, ilgili composable yeniden çalışır (recomposition).
 *
 * - **Composition:** Composable fonksiyonlar ilk kez çalıştığında oluşan UI ağacıdır.
 * - **Recomposition:** State değiştiğinde, ilgili composable fonksiyonların tekrar çalışmasıdır.
 *
 * ## State Tanımlama Yöntemleri
 * - `remember { mutableStateOf(default) }` ile sadece recomposition arasında state saklanır.
 * - `rememberSaveable { mutableStateOf(default) }` ile state, recomposition ve konfigürasyon değişikliklerinde (örn. ekran döndürme) korunur.
 * - `by` delegate ile daha okunabilir state tanımı yapılabilir.
 */

// Yanlış Kullanım: Observable olmayan mutable obje ile state
@Composable
fun WrongMutableListExample() {
    val items = remember { mutableListOf("A", "B") } // Yanlış: mutableListOf observable değil
    Column {
        Button(onClick = { items.add("C") }) { Text("Ekle") }
        Text("Size: ${items.size}") // UI güncellenmez!
    }
}

// Doğru Kullanım: Observable ve immutable liste ile state
@Composable
fun CorrectListExample() {
    var items by remember { mutableStateOf(listOf("A", "B")) }
    Column {
        Button(onClick = { items = items + "C" }) { Text("Ekle") }
        Text("Size: ${items.size}") // UI güncellenir
    }
}

/**
 * ## State Hoisting (State'i Yukarı Taşımak)
 * State'i composable'ın dışına taşıyarak, composable'ı stateless ve daha tekrar kullanılabilir yaparsın. Bu, unidirectional data flow sağlar: State yukarıdan aşağı iner, event'ler aşağıdan yukarı çıkar.
 *
 * ## Stateless ve Stateful Composable'lar
 * - **Stateful:** State'i içinde tutan composable.
 * - **Stateless:** State'i dışarıdan alan ve sadece gösteren composable.
 */

@Composable
fun StatefulCounter() { //State'i kendi içinde tutan (stateful) composable.
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count = count, onIncrement = { count++ })
}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit) { // State'i dışarıdan alan ve sadece gösteren (stateless) composable.
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Count: $count", modifier = Modifier.padding(end = 8.dp))
        Button(onClick = onIncrement) { Text("+1") }
    }
}
/**
 * StatelessCounter fonksiyonunun içinde herhangi bir var, remember, mutableStateOf veya başka bir state tutucu yoktur.
 * count parametresi, fonksiyona dışarıdan (yani parent composable'dan) verilir.
 * onIncrement fonksiyonu da yine dışarıdan gelir ve butona tıklanınca çağrılır.
 * StatelessCounter, sadece aldığı count değerini ekranda gösterir ve butona tıklanınca dışarıdan verilen fonksiyonu tetikler.
 * Yani, kendi içinde state tutmaz, sadece dışarıdan aldığı state'i gösterir ve event'i dışarıya iletir.
 */


/**
 * ## Farklı State Kaynakları
 * - **Flow:** `collectAsStateWithLifecycle()` veya `collectAsState()` ile Compose State'e dönüştürülür.
 * - **LiveData:** `observeAsState()` ile kullanılır.
 * - **RxJava:** `subscribeAsState()` ile kullanılır.
 * - **Custom observable:** `produceState` ile Compose State'e dönüştürülebilir.
 *
 * ### Hangi Durumda Hangisi? Avantaj & Dezavantajlar
 *
 * ### Flow (StateFlow, SharedFlow)
 * - Avantaj: Modern, coroutine tabanlı, lifecycle-aware, multiplatform, performanslı, error handling ve transformation desteği yüksek.
 * - Kullanım: ViewModel ile veri akışı, asenkron ve sürekli değişen veri, multiplatform.
 *
 * ### LiveData
 * - Avantaj: Android'e özgü, lifecycle-aware, eski projelerde kolay entegrasyon, ViewModel ile uyumlu.
 * - Dezavantaj: Sadece Android, modern özellikler ve multiplatform desteği yok, ek Compose bağımlılığı gerekir.
 * - Kullanım: Mevcut LiveData tabanlı projeler, hızlı migration, klasik MVVM.
 *
 * ### RxJava
 * - Avantaj: Çok güçlü operatörler, büyük/legacy projelerde yaygın, Compose ile subscribeAsState kolay entegrasyon.
 * - Dezavantaj: Öğrenme eğrisi yüksek, modern Android'de Flow tercih edilir, multiplatform desteği zayıf.
 * - Kullanım: RxJava altyapısı olan projeler, karmaşık reaktif zincirler, migration yapılmadan Compose'a geçiş.
 *
 * ### Custom Observable (produceState, callback, listener)
 * - Avantaj: Her türlü observable kaynağı Compose State'e dönüştürmek için esneklik sağlar, 3rd party SDK/callback desteği.
 * - Dezavantaj: Manuel yönetim, lifecycle ve memory leak riskleri, dikkat gerektirir.
 * - Kullanım: Standart observable dışında veri kaynağı, kendi event/callback sistemini Compose ile entegre etmek.
 *
 * ---
 *
 * |                | Modernlik | Android’e Özgü | Lifecycle Awareness | Multiplatform | Kolaylık | Performans | Kullanım Durumu                |
 * |----------------|-----------|----------------|---------------------|---------------|----------|------------|-------------------------------|
 * | Flow           | Yüksek    | Hayır          | Evet                | Evet          | Orta     | Yüksek     | Yeni projeler, asenkron veri   |
 * | LiveData       | Orta      | Evet           | Evet                | Hayır         | Kolay    | Orta       | Eski projeler, hızlı geçiş     |
 * | RxJava         | Orta      | Hayır          | Evet                | Hayır         | Zor      | Yüksek     | Legacy, karmaşık zincirler     |
 * | produceState   | Yüksek    | Hayır          | Manuel              | Evet          | Orta     | Yüksek     | Custom observable, callback    |
 */

// Flow ile state yönetimi
class CounterFlowHolder {
    private val _counter = MutableStateFlow(0) // StateFlow: observable state
    val counter: StateFlow<Int> = _counter.asStateFlow() // dışarıya sadece StateFlow olarak açılır
    fun increment() {
        _counter.value++ // State'i günceller, tüm gözlemciler (UI dahil) yeni değeri alır
    }
}

@Composable
fun FlowCounterExample(holder: CounterFlowHolder = CounterFlowHolder()) {
    val count by holder.counter.collectAsState() // Flow'u Compose State'e dönüştürür, her değişiklikte recomposition tetikler
    Button(onClick = { holder.increment() }) {
        Text("Flow Count: $count") // State değiştikçe otomatik güncellenir
    }
}

// LiveData ile state yönetimi
class CounterLiveDataHolder {
    private val _counter = MutableLiveData(0)
    val counter: LiveData<Int> = _counter
    fun increment() {
        _counter.value = (_counter.value ?: 0) + 1
    }
}

@Composable
fun LiveDataCounterExample(holder: CounterLiveDataHolder = CounterLiveDataHolder()) {
    val count by holder.counter.observeAsState(0) // LiveData'yı Compose State'e dönüştürür, her değişiklikte recomposition tetikler
    Button(onClick = { holder.increment() }) {
        Text("LiveData Count: $count") // State değiştikçe otomatik güncellenir
    }
}

/**
 * ## State Holder Pattern
 * Karmaşık state ve logic için state holder class (örn. ViewModel veya plain state holder) kullanılır. Bu, UI ile business logic'in ayrılmasını sağlar.
 */

// State Holder Pattern (Plain class, ViewModel olmadan)
class FormStateHolder {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    fun clear() {
        name = ""
        email = ""
    }
}

@Composable
fun RememberFormStateHolder(): FormStateHolder {
    return remember { FormStateHolder() }
}

@Composable
fun FormScreen() {
    val formState = RememberFormStateHolder() //remember ile bir FormStateHolder nesnesi oluşturuyor ve bu nesneyi, composable’ın ömrü boyunca saklıyor.
    // recomposition olduğunda aynı nesne kullanılmaya devam ediyor (state kaybolmuyor).
    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(value = formState.name, onValueChange = { formState.name = it }, label = { Text("Name") })
        OutlinedTextField(value = formState.email, onValueChange = { formState.email = it }, label = { Text("Email") })
        Button(onClick = { formState.clear() }) { Text("Temizle") }
    }
}

/**
 * ##Dikkat Edilmesi Gerekenler
 *
 * - **Mutable olmayan observable kullan:** Observable olmayan mutable objeler (örn. ArrayList) ile state tutma! Bunlar değişince Compose fark etmez ve UI güncellenmez.
 *   - Yanlış:
 *     val items = remember { mutableListOf("A", "B") }
 *     items.add("C") // UI güncellenmez!
 *     ```
 *   - Doğru:
 *     var items by remember { mutableStateOf(listOf("A", "B")) }
 *     items = items + "C" // UI güncellenir
 *     ```
 *
 * - **State'i doğru yerde tut:** State'i gereğinden fazla yukarıda veya aşağıda tutmak, kodun karmaşıklaşmasına veya gereksiz recomposition'a neden olur. State'i kullanan en düşük ortak parent'a taşı.
 *   - Yanlış:
 *     @Composable
 *     fun Parent() {
 *         var text by remember { mutableStateOf("") } // Tüm child'lar gereksiz yere yeniden çizilir
 *         Child()
 *         OutlinedTextField(value = text, onValueChange = { text = it })
 *     }
 *     ```
 *
 * - **remember/rememberSaveable farkı:** `remember` sadece recomposition arasında saklar, `rememberSaveable` ise konfigürasyon değişikliklerinde de saklar.
 *   - Örnek:
 *     var name by remember { mutableStateOf("") } // Ekran döndürülünce sıfırlanır
 *     var name by rememberSaveable { mutableStateOf("") } // Ekran döndürülse de korunur
 *     ```
 *
 * - **Key ile remember kullanımı:** Eğer bir state, belirli bir input değiştiğinde sıfırlanmalıysa, `remember(key1 = ...)` kullan. Key değişirse, state sıfırlanır.
 *   - Örnek:
 *     val brush = remember(avatarRes) { createBrush(avatarRes) }
 *     ```
 *
 * - **derivedStateOf ile performans:** Hesaplanmış state'ler için `derivedStateOf` kullanarak gereksiz recomposition'ı önle.
 *   - Örnek:
 *     val isLong by remember(text) { derivedStateOf { text.length > 10 } }
 *     ```
 *
 * - **Preview'da state:** Compose Preview'da state'in ilk değeriyle gösterilir, interaktif değildir.
 *   - Preview'da butonlar ve inputlar tıklanamaz, sadece başlangıç state'i gösterilir.
 *
 * - **Unidirectional data flow:** State yukarıdan aşağı iner, event'ler aşağıdan yukarı çıkar. Bu, kodun okunabilirliğini ve test edilebilirliğini artırır.
 *   - Örnek:
 *     @Composable
 *     fun Parent() {
 *         var count by remember { mutableStateOf(0) }
 *         Child(count, onIncrement = { count++ })
 *     }
 *     ```
 * - **State'i ViewModel'de tutmak:** Ekranlar arası veya process ölçeğinde state gerekiyorsa, ViewModel kullanmak daha güvenlidir.
 *
 * - **Anti-pattern:** State'i global veya singleton objelerde tutmak, Compose'un reaktif yapısını bozar.
 *   - Yanlış:
 *     object GlobalState {
 *         var count = 0
 *     }
 *     // UI güncellenmez!
 *     ```
 */

// derivedStateOf ile performans optimizasyonu
@Composable
fun DerivedStateExample() {
    var text by remember { mutableStateOf("") }
    val isLong by remember(text) { derivedStateOf { text.length > 10 } }
    Column {
        OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("Input") })
        if (isLong) Text("Çok uzun!")
    }
}

// rememberSaveable ile custom saver
@Parcelize
data class City(val name: String, val country: String) : Parcelable

@Composable
fun CitySaveableExample() {
    var city by rememberSaveable { mutableStateOf(City("Istanbul", "Turkey")) }
    Column {
        Text("City: ${city.name}, ${city.country}")
        Button(onClick = { city = City("Berlin", "Germany") }) { Text("Şehri Değiştir") }
    }
}

// Anti-pattern: State'i global objede tutmak
object GlobalState {
    var count = 0 // Yanlış: Compose ile uyumlu değil, recomposition tetiklemez
}

@Composable
fun GlobalStateExample() {
    Button(onClick = { GlobalState.count++ }) {
        Text("Global Count: ${GlobalState.count}") // UI güncellenmez!
    }
}

// State'i gereksiz yere parent'ta tutmak
@Composable
fun ParentStateAntiPattern() {
    var text by remember { mutableStateOf("") } // Tüm child'lar için gereksiz recomposition
    Column {
        ChildComposable()
        OutlinedTextField(value = text, onValueChange = { text = it })
    }
}

@Composable
fun ChildComposable() {
    // Bu composable, parent'taki state değişince gereksiz yere recomposition olur
    Text("Ben child'ım")
}

/**
 * # Sık Yapılan Hatalar ve Anti-pattern'ler
 *
 * - Observable olmayan mutable objelerle state tutmak (örn. mutableListOf, ArrayList)
 * - State'i gereksiz yere global veya üst parent'ta tutmak
 * - State'i composable dışında (örn. companion object, singleton) tutmak
 * - State'i birden fazla yerde kopyalamak (tek source of truth ilkesine aykırı)
 * - State değişimini doğrudan UI thread dışında yapmak (Compose thread-safe değildir)
 */

