package com.dilara.composeexample.architecture

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * ============================================================================
 * 🟦 JETPACK COMPOSE'DA COMPOSABLE YAŞAM DÖNGÜSÜ & RECOMPOSITION DOKÜMANI
 * ============================================================================
 */

/**
 * 🔵 1. COMPOSABLE YAŞAM DÖNGÜSÜ NEDİR?
 * -----------------------------------------------------------------------------
 * - Bir composable, Composition'a girer, 0 veya daha fazla kez yeniden çizilir (recompose edilir), sonra çıkar.
 * - Yani: Oluşur → (0 veya daha fazla kez) Yeniden Çizilir → Yok edilir.
 */

@Composable
fun CounterExample() {
    var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) {
        Text("Tıkla: $count")
    }
}

// -----------------------------------------------------------------------------

/**
 * 🟢 2. COMPOSITION VE RECOMPOSITION NEDİR?
 * -----------------------------------------------------------------------------
 * - Composition: Composable'ların UI ağacının ilk kez oluşturulmasıdır.
 * - Recomposition: State değiştiğinde, Compose ilgili composable'ı tekrar çalıştırır ve UI'ı günceller.
 * - Sadece recomposition ile UI güncellenir. Composition'ı değiştirmek için tek yol recomposition'dır.
 */

@Composable
fun NameGreeting(name: String) {
    Text("Merhaba, $name!")
}

// -----------------------------------------------------------------------------

/**
 * 🟣 3. CALL SITE VE KİMLİK (IDENTITY) MANTIĞI
 * -----------------------------------------------------------------------------
 * - Aynı composable birden fazla yerde çağrılırsa, her çağrı kendi yaşam döngüsüne sahip olur.
 */

@Composable
fun MultipleTextExample() {
    Column {
        Text("Hello") // 1. çağrı
        Text("World") // 2. çağrı
    }
}

// -----------------------------------------------------------------------------

/**
 * 🟠 4. KEY KULLANIMI VE LİSTELERDE KİMLİK
 * -----------------------------------------------------------------------------
 * - Listelerde composable'ların kimliğini korumak için key kullanılır.
 * - LazyColumn gibi yapılarda items(movies, key = { it.id }) { ... } şeklinde de kullanılır.
 */

data class Movie(val id: Int, val name: String)

@Composable
fun MovieListWithKey(movies: List<Movie>) {
    Column {
        for (movie in movies) {
            key(movie.id) {
                MovieOverview(movie)
            }
        }
    }
}

@Composable
fun MovieOverview(movie: Movie) {
    Text(movie.name)
}

// -----------------------------------------------------------------------------

/**
 * 🟡 5. RECOMPOSITION VE STATE YÖNETİMİ
 * -----------------------------------------------------------------------------
 * - State<T> değişirse, o state'i okuyan composable ve alt composable'lar yeniden çizilir.
 * - remember ile tutulan state, recomposition sırasında kaybolmaz.
 * - rememberSaveable ile state, ekran döndürmede de korunur.
 */

@Composable
fun RememberVsRememberSaveableExample() {
    var text1 by remember { mutableStateOf("") }
    var text2 by rememberSaveable { mutableStateOf("") }
    Column {
        Text("remember: Ekran döndüğünde kaybolur")
        TextField(value = text1, onValueChange = { text1 = it })
        Spacer(Modifier.height(8.dp))
        Text("rememberSaveable: Ekran döndüğünde korunur")
        TextField(value = text2, onValueChange = { text2 = it })
    }
}

// -----------------------------------------------------------------------------

/**
 * 🟤 6. STABLE VE UNSTABLE TİPLER
 * -----------------------------------------------------------------------------
 * - Compose, parametrelerin değişmediğini anlarsa composable'ı atlayabilir (skip).
 * - Stable tipler: Int, String, State<T>, immutable data class'lar, vs.
 * - Unstable tipler: Mutable public property'li class'lar, interface'ler (eğer @Stable ile işaretlenmemişse).
 * - @Stable ile işaretlenmiş tipler Compose tarafından daha akıllı yönetilir.
 *
 * Stable ve Unstable tipler neden önemli?
 * - Stable tipler ile Compose, gereksiz recomposition'ı önler ve performans artar.
 * - Unstable tipler ile Compose, her ihtimale karşı composable'ı yeniden çalıştırır.
 *
 * İpucu:
 * - Data class’larda mümkünse sadece val kullan, public var’lardan kaçın, interface’leri @Stable ile işaretle.
 */

// --- Stable Data Class Örneği ---
data class User(val name: String, val age: Int) // Sadece val, immutable

@Composable
fun UserCard(user: User) {
    Text(user.name)
}
// user değişmedikçe, UserCard skip edilebilir.

// --- Unstable Data Class Örneği ---
data class MutableUser(var name: String, var age: Int) // var ile mutable

@Composable
fun MutableUserCard(user: MutableUser) {
    Text(user.name)
}
// MutableUser parametresi değişmese bile, Compose bu tipin değişip değişmediğinden emin olamaz, skip etmez.

// --- @Stable ile Interface'i Stabil Yapmak ---

@Stable
interface UiState<T> {
    val value: T?
    val hasError: Boolean
}

class StringUiState(override val value: String?, override val hasError: Boolean) : UiState<String>

@Composable
fun StateCard(state: UiState<String>) {
    Text(state.value ?: "Boş")
}
// @Stable ile işaretlenirse, Compose bu interface’in implementasyonlarının stabil olduğunu varsayar ve skip edebilir.

// --- MutableList ve StableList Farkı ---
@Composable
fun ListExample(list: List<String>) {
    Text(list.joinToString())
}

val stableList = listOf("a", "b") // Stable
val mutableList = mutableListOf("a", "b") // Unstable
// stableList ile skip edilebilir, mutableList ile skip edilemez.

// -----------------------------------------------------------------------------
/**
 * 🟧 7. SIDE EFFECTS (YAN ETKİLER) VE LIFECYCLE
 * -----------------------------------------------------------------------------
 * - Side effect (yan etki) yönetimi için LaunchedEffect, DisposableEffect, SideEffect kullanılır.
 * - Side effect'ler, recomposition sırasında gereksiz yere tekrar başlatılmamalı.
 *
 * 🔹 LaunchedEffect: State veya parametre değiştiğinde bir defa çalışan side effect. Genellikle API çağrısı, animasyon başlatma gibi işlemler için kullanılır.
 * 🔹 DisposableEffect: Composable ekrana geldiğinde başlar, ekrandan çıkınca temizlenir. Örn: Listener ekleme/çıkarma, kaynak yönetimi.
 * 🔹 SideEffect: Her recomposition sonrası çalışan side effect. Compose dışı bir şeyi güncellemek için (örn: bir global değişken).
 *
 * Hangi durumda hangisi?
 * - API çağrısı, animasyon, state'e bağlı işlemler: LaunchedEffect
 * - Event listener, BroadcastReceiver, kaynak aç/kapat: DisposableEffect
 * - Compose dışı bir şeyi her recompose'da güncelle: SideEffect
 */

/**
 * LaunchedEffect Kullanımı: Sayaç değişince bir defa tetiklenir.
 */
@Composable
fun LaunchedEffectExample(count: Int) {
    LaunchedEffect(count) {
        println("Count değişti: $count")
        // Burada API çağrısı, animasyon başlatma vs. yapılabilir
    }
}

/**
 * DisposableEffect Kullanımı: Ekrana gelince başlar, çıkınca temizlenir.
 */
@Composable
fun DisposableEffectExample(key: String) {
    DisposableEffect(key) {
        println("Effect başlatıldı: $key")
        // Örn: Listener ekle
        onDispose {
            println("Effect temizlendi: $key")
            // Listener kaldır
        }
    }
}

/**
 * SideEffect Kullanımı: Her recomposition sonrası çalışır.
 */
@Composable
fun SideEffectExample() {
    SideEffect {
        println("Her recomposition sonrası çalışır!")
        // Örn: Compose dışı bir global değişkeni güncelle
    }
}

/**
 * 🟥 8. SIK YAPILAN HATALAR & PRATİK İPUÇLARI
 * -----------------------------------------------------------------------------
 */

/**
 *  Listelerde key kullanılmazsa, side effect'ler gereksiz yere tekrar başlatılır.
 */
@Composable
fun WrongKeyListExample() {
    var movies by remember { mutableStateOf(listOf(Movie(1, "A"), Movie(2, "B"))) }
    Column {
        Button(onClick = {
            movies = listOf(Movie(3, "C")) + movies
        }) {
            Text("Başa Film Ekle")
        }
        // Burada key kullanılmazsa, MovieOverviewWithSideEffect içindeki yan etki her item için tekrar başlar
        for (movie in movies) {
            MovieOverviewWithSideEffect(movie)
        }
    }
}

@Composable
fun MovieOverviewWithSideEffect(movie: Movie) {
    LaunchedEffect(movie.id) {
        println("Film yükleniyor: ${movie.name}")
    }
    Text(movie.name)
}

/**
 *  State'i yanlış scope'ta (her recomposition'da) oluşturmak, state kaybına yol açar.
 */
@Composable
fun WrongStateScopeExample() {
    // Yanlış: Her recomposition'da yeni state oluşur, değer kaybolur
    var count = 0
    Button(onClick = { count++ }) {
        Text("Tıkla: $count")
    }
}

@Composable
fun CorrectStateScopeExample() {
    // Doğru: State remember ile tutulur
    var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) {
        Text("Tıkla: $count")
    }
}

/**
 *  Yan etkiyi (side effect) doğrudan composable'ın gövdesinde başlatmak recomposition'da tekrar tekrar çağrılmasına sebep olur.
 */
@Composable
fun WrongSideEffectExample() {
    // Yanlış: Her recomposition'da API çağrısı yapılır
    // apiCall()
    Text("Yanlış kullanım!")
}

@Composable
fun CorrectSideEffectExample() {
    // Doğru: Yan etki LaunchedEffect içinde başlatılır
    LaunchedEffect(Unit) {
        // apiCall()
    }
    Text("Doğru kullanım!")
}

/**
 *   Composable fonksiyonun Unit dışında bir değer döndürmesi, onun skip edilememesine sebep olur.
 */

// Yanlış:
// @Composable
// fun WrongComposable(): Int { ... }
// Doğru:
// @Composable
// fun CorrectComposable() { ... }

/**
 *  Compose'da recomposition, sadece değişen subtree'yi etkiler, tüm UI yeniden çizilmez.
 */
@Composable
fun PartialRecompositionExample() {
    var count by remember { mutableStateOf(0) }
    Column {
        Text("Statik metin") // Bu Text recompose olmaz
        Button(onClick = { count++ }) {
            Text("Tıkla: $count") // Sadece bu Button ve Text recompose olur
        }
    }
}

/**
 *  rememberSaveable ile state'i ekran döndürmede koru.
 */
@Composable
fun RememberSaveableExample() {
    var text by rememberSaveable { mutableStateOf("") }
    TextField(value = text, onValueChange = { text = it })
}

/**
 * Yanlış yerde remember kullanmak, beklenmedik state kayıplarına yol açar.
 */
@Composable
fun WrongRememberScopeExample(input: String) {
    // Yanlış: Parametreye bağlı state, recomposition'da sıfırlanır
    val state = remember { mutableStateOf(input) }
    TextField(value = state.value, onValueChange = { state.value = it })
}

@Composable
fun CorrectRememberScopeExample(input: String) {
    // Doğru: Key ile remember kullanılır
    val state = remember(input) { mutableStateOf(input) }
    TextField(value = state.value, onValueChange = { state.value = it })
}


/**
 * 🟩 9. EKSTRA: GERÇEK HAYATTAN PRATİK ÖRNEKLER
 * -----------------------------------------------------------------------------
 * - Listeye yeni eleman eklenince recomposition davranışı
 * - Yanlış key kullanımı sonucu side effect'in gereksiz tetiklenmesi
 * - Aynı composable'ın farklı parametrelerle çağrılması
 * - State'in parent ve child composable'lar arasında paylaşımı
 */

// --- Listeye yeni eleman eklenince recomposition davranışı ---
@Composable
fun DynamicMovieListExample() {
    var movies by remember { mutableStateOf(listOf(Movie(1, "Inception"), Movie(2, "Matrix"))) }
    Column {
        Button(onClick = {
            movies = movies + Movie(movies.size + 1, "Yeni Film ${movies.size + 1}")
        }) {
            Text("Film Ekle")
        }
        MovieListWithKey(movies)
    }
}

// --- Aynı composable'ın farklı parametrelerle çağrılması ---
@Composable
fun MultipleGreetingExample() {
    Column {
        NameGreeting("Dilara")
        NameGreeting("Yavuz Selim")
        NameGreeting("Nurefşan")
    }
}

// --- State'in parent ve child composable'lar arasında paylaşımı ---
@Composable
fun ParentChildStateSharingExample() {
    var parentState by remember { mutableStateOf("Parent State") }
    Column {
        Text("Parent: $parentState")
        ChildComposable(state = parentState, onStateChange = { parentState = it })
    }
}

@Composable
fun ChildComposable(state: String, onStateChange: (String) -> Unit) {
    Row {
        Text("Child: $state")
        Spacer(Modifier.height(8.dp))
        Button(onClick = { onStateChange("Child'dan güncellendi!") }) {
            Text("Güncelle")
        }
    }
}
