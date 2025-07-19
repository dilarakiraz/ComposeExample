/**
 * # Compose'da UI State Nasıl Saklanır? (Detaylı Açıklama)
 *
 * ## Neden UI State Saklanmalı?
 * - Android'de konfigürasyon değişikliği (örn. ekran döndürme) veya sistemin uygulamayı arka planda öldürmesi gibi durumlarda, UI state kaybolabilir.
 * - Kullanıcı deneyimi için, en azından kullanıcı girdisi ve navigasyonla ilgili state'ler korunmalı (örn. scroll pozisyonu, seçili item ID'si, form inputları).
 *
 * ## State Kaybı Ne Zaman Olur?
 * - Konfigürasyon değişikliği: Activity yeniden yaratılır (örn. ekran döndürme).
 * - Sistem kaynakları için process ölümü: Uygulama arka planda ve sistem bellek boşaltır.
 * - Kullanıcı uygulamayı kapatırsa, geçici state'in kaybolması doğaldır.
 *
 * ## Hangi State'i Saklamalı?
 * - Kullanıcı girdisi (form, arama, seçimler)
 * - Navigasyonla ilgili state (scroll pozisyonu, seçili item ID'si)
 * - Kısa süreli, kullanıcıya anlamlı olan geçici state
 *
 * ## UI Logic için State Saklama
 * - State, UI'da (composable fonksiyonlarda veya Composition'a bağlı state holder class'larda) tutuluyorsa, **rememberSaveable** ile saklanmalı.
 * - rememberSaveable, state'i bir Bundle içinde saklar ve konfigürasyon değişikliğinde veya process ölümü sonrası geri yükler.
 * - Primitive tipler otomatik saklanır. Data class gibi tipler için Parcelize, listSaver/mapSaver veya custom Saver kullanılabilir.
 *
 * ## rememberSaveable Kullanımında Sınırlar
 * - Bundle limiti vardır! Büyük objeleri burada saklama, TransactionTooLargeException alırsın.
 * - Sadece minimum gerekli state'i (örn. ID, scroll pozisyonu, input text) sakla.
 * - Büyük veriler için persistent storage (Room, dosya, vs.) kullan.
 *
 * ## LazyListState gibi özel state'ler
 * - rememberLazyListState, kendi Saver'ı ile scroll pozisyonunu saklar.
 *
 * ## Business Logic için State Saklama (ViewModel)
 * - State ViewModel'de tutuluyorsa, konfigürasyon değişikliğinde otomatik olarak korunur.
 * - Ancak, sistem process'i öldürürse ViewModel de kaybolur. Bu durumda **SavedStateHandle** kullanılır.
 * - SavedStateHandle, küçük ve basit state'leri Bundle ile saklar. Büyük/karmaşık veriler için persistent storage kullan.
 *
 * ## StateFlow ile SavedStateHandle
 * - SavedStateHandle ile StateFlow kullanarak state'i flow olarak da saklayabilirsin.
 *
 * ## Test ve Doğrulama
 * - rememberSaveable ile saklanan state'in doğru geri yüklendiğini StateRestorationTester gibi Compose test araçlarıyla doğrulayabilirsin.
 *
 * | Olay                        | UI logic (rememberSaveable) | Business logic (ViewModel) |
 * |-----------------------------|-----------------------------|----------------------------|
 * | Konfigürasyon değişikliği   | Evet                        | Otomatik                   |
 * | Process ölümü               | Evet                        | SavedStateHandle           |
 */

import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.parcelize.Parcelize

// --- Primitive tip örneği ---
@Composable
fun SaveableCounter() {
    var count by rememberSaveable { mutableStateOf(0) }
    Button(onClick = { count++ }) { Text("Sayaç: $count") }
}

// --- Data class için Parcelize örneği ---
@Parcelize
data class User(val name: String, val age: Int) : Parcelable

@Composable
fun SaveableUser() {
    var user by rememberSaveable { mutableStateOf(User("Dilara", 24)) }
    Column {
        Text("Ad: ${user.name}, Yaş: ${user.age}")
        Button(onClick = { user = user.copy(age = user.age + 1) }) { Text("Yaşı Artır") }
    }
}

// --- listSaver ile custom state örneği ---
@Composable
fun SaveableListExample() {
    val saver = listSaver<List<String>, String>( //listSaver, Compose'un, liste gibi özel tipleri (örneğin List<String>) Bundle'a kaydedip geri yükleyebilmesi için kullanılır.
        save = { it },
        restore = { it }
    )
    var items by rememberSaveable(stateSaver = saver) {
        mutableStateOf(listOf("A", "B"))
    } //rememberSaveable ile birlikte kullanıldığında, listedeki veriler Activity yeniden yaratıldığında (örn. ekran döndürme) veya process ölümü sonrası geri gelir.
    Column {
        Button(onClick = { items = items + "C" }) { Text("Ekle") }
        Text("Liste: $items")
    }
}//rememberSaveable primitive tipleri (int, String, Boolean) otomatik saklar.List<String> gibi koleksiyonları otomatik saklayamaz.


// --- mapSaver ile custom state örneği ---
@Composable
fun SaveableMapExample() {
    val saver = mapSaver(
        save = { mapOf("ad" to it[0], "soyad" to it[1]) },
        restore = { listOf(it["ad"] as String, it["soyad"] as String) }
    )
    var fields by rememberSaveable(stateSaver = saver) { mutableStateOf(listOf("", "")) }
    Column {
        OutlinedTextField(value = fields[0], onValueChange = { fields = listOf(it, fields[1]) }, label = { Text("Ad") })
        OutlinedTextField(value = fields[1], onValueChange = { fields = listOf(fields[0], it) }, label = { Text("Soyad") })
    }
}//rememberSaveable ile primitive tipler otomatik saklanır, ama birden fazla alanı olan custom state'ler için Compose'a nasıl kaydedip geri yükleyeceğini belirtmek gerekir.
//mapSaver, bir objeyi (burada List<String>) bir Map'e çevirip Bundle'a kaydeder, sonra geri yükler.


// --- Custom Saver ile özel tip örneği (TextFieldValue) ---
@Composable
fun SaveableTextFieldValue() {
    var value by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    BasicTextField(value = value, onValueChange = { value = it })
}//TextFieldValue, sadece metni değil, aynı zamanda imleç (cursor) pozisyonu, seçili alan gibi bilgileri de tutan özel bir Compose tipi.Sadece String ile saklarsan, imleç pozisyonu gibi detaylar kaybolur.
//rememberSaveable ile birlikte, Compose'un kendi sağladığı TextFieldValue.Saver'ı kullanarak bu özel tipin tüm özelliklerini Bundle'a kaydedip geri yükleyebilirsin.


// --- rememberSaveable ile boolean state ---
data class Message(val content: String, val timestamp: String)

@Composable
fun ChatBubble(message: Message) {
    var showDetails by rememberSaveable { mutableStateOf(false) }
    ClickableText(
        text = AnnotatedString(message.content),
        onClick = { showDetails = !showDetails }
    )
    if (showDetails) {
        Text(message.timestamp)
    }
}

// --- LazyListState ile scroll pozisyonu saklama ---
@Composable
fun SaveableScrollList() {
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    LazyColumn(state = listState, modifier = Modifier.height(150.dp)) {
        items(50) { Text("Satır $it", Modifier.padding(8.dp)) }
    }
}

// --- Büyük objeleri saklama! Sadece ID veya key sakla ---
@Composable
fun BigListStateExample() {
    val items = remember { List(1000) { "Item $it" } }
    var selectedId by rememberSaveable { mutableStateOf(-1) }
    LazyColumn(Modifier.height(150.dp)) {
        items(items) { item ->
            val id = item.substringAfter(" ").toInt()
            Button(
                onClick = { selectedId = id },
                colors = if (selectedId == id) ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary) else ButtonDefaults.buttonColors()
            ) { Text(item) }
        }
    }
    Text("Seçili: $selectedId")
}

// --- rememberSaveable(key) ile state sıfırlama ---
@Composable
fun ResetOnKeyChange(userId: String) {
    var note by rememberSaveable(userId) { mutableStateOf("") }
    OutlinedTextField(value = note, onValueChange = { note = it }, label = { Text("Notunuz") })
}

// --- ViewModel ve SavedStateHandle ile state saklama ---
class TestSavedStateHandle {
    private val map = mutableMapOf<String, Any?>()
    fun <T> get(key: String): T? = map[key] as? T
    fun <T> set(key: String, value: T) {
        map[key] = value
    }
}

class MyViewModel(val handle: TestSavedStateHandle = TestSavedStateHandle()) {
    var counter: Int
        get() = handle.get("counter") ?: 0
        set(value) = handle.set("counter", value)
}

@Composable
fun ViewModelSaveableExample(vm: MyViewModel = remember { MyViewModel() }) {
    Column {
        Text("Sayaç: ${vm.counter}")
        Button(onClick = { vm.counter++ }) { Text("Artır") }
    }
}

// --- StateFlow ile SavedStateHandle örneği ---
class StateFlowViewModel {
    private val _filterType = MutableStateFlow("ALL")
    val filterType: StateFlow<String> =
        _filterType.asStateFlow() //Dışarıya sadece okunabilir bir StateFlow açılır. Böylece dışarıdan sadece okunabilir, değiştirilemez.(encapsulation)

    fun setFilter(newType: String) {
        _filterType.value = newType
    }
}

@Composable
fun StateFlowExample(vm: StateFlowViewModel = remember { StateFlowViewModel() }) {
    val filter by vm.filterType.collectAsState() //filterType bir StateFlow olduğu için, Compose'da collectAsState() ile gözlemlenir.
    Column {
        Text("Filtre: $filter")
        Button(onClick = { vm.setFilter("RECENT") }) { Text("Son Eklenenler") }
        Button(onClick = { vm.setFilter("ALL") }) { Text("Tümü") }
    }
} 