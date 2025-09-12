import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * # Compose UI Mimarisi (Architecting your Compose UI)
 *
 * ## Neden UI Mimarisi Önemli?
 * - Compose'da UI immutable'dır - çizildikten sonra güncellenemez.
 * - Kontrol edebileceğin tek şey UI'ın state'idir.
 * - State değiştiğinde Compose, değişen UI ağacının parçalarını yeniden oluşturur.
 * - Composables state kabul eder ve event'ler açığa çıkarır.
 *
 * ## Unidirectional Data Flow (Tek Yönlü Veri Akışı)
 * - State aşağı doğru akar, event'ler yukarı doğru akar.
 * - Bu pattern ile UI'ı state'i saklayan ve değiştiren kısımlardan ayırabilirsin.
 * - Avantajları: Test edilebilirlik, state encapsulation, UI tutarlılığı.
 *
 * ## Composable Parametreleri Nasıl Tanımlanmalı?
 * - Composable ne kadar yeniden kullanılabilir veya esnek?
 * - State parametreleri bu composable'ın performansını nasıl etkiler?
 * - Her composable mümkün olan en az bilgiyi tutmalı.
 * - Çok fazla parametre varsa, bunları bir class'ta grupla.
 *
 * ## Events in Compose
 * - Her input event olarak temsil edilmeli: tıklama, metin değişimi, timer'lar.
 * - ViewModel bu event'leri handle etmeli ve UI state'ini güncellemeli.
 * - UI layer asla event handler dışında state değiştirmemeli.
 * - Immutable değerler ve event handler lambda'ları tercih et.
 *
 * ## ViewModel, State ve Event Örneği
 * - UI state'i observable state holder'lar ile açığa çıkarılmalı.
 * - ViewModel, UI'dan veya diğer katmanlardan gelen event'leri handle etmeli.
 * - State holder'lar StateFlow, LiveData veya Observable olabilir.
 */

// --- State ve Event Örneği ---
@Composable
fun SimpleTextFieldExample() {
    var name by remember { mutableStateOf("") }
    OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        label = { Text("İsim") }
    )
}

// ---Unidirectional Data Flow Örneği ---
@Composable
fun CounterScreen() {
    var count by remember { mutableIntStateOf(0) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Sayaç: $count")
        Button(onClick = { count++ }) { Text("Artır") }
        Button(onClick = { count-- }) { Text("Azalt") }
    }
}

// --- Composable Parametreleri - İyi Örnek ---
@Composable
fun Header(title: String, subtitle: String) {
    Column {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        Text(text = subtitle, style = MaterialTheme.typography.bodyMedium)
    }
}

// ---Composable Parametreleri - Kötü Örnek ---
data class News(val title: String, val subtitle: String, val content: String, val author: String, val date: String)

@Composable
fun HeaderBad(news: News) {
    Column {
        Text(text = news.title, style = MaterialTheme.typography.headlineMedium)
        Text(text = news.subtitle, style = MaterialTheme.typography.bodyMedium)
    }
}

// ---Generic TopAppBar Composable ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppTopAppBar(topAppBarText: String, onBackPressed: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Geri"
                )
            }
        }
    )
}

// --- Sealed Class ile UI State Modelleme ---
sealed class UiState {
    object SignedOut : UiState()
    object InProgress : UiState()
    data class Error(val message: String) : UiState()
    object SignedIn : UiState()
}

// ---ViewModel ile State Management ---
class UiStateViewModel {
    private val _uiState = mutableStateOf<UiState>(UiState.SignedOut)
    val uiState: MutableState<UiState> = _uiState

    fun onSignIn() {
        _uiState.value = UiState.InProgress
        // API çağrısı
    }

    fun onSignOut() {
        _uiState.value = UiState.SignedOut
    }
}

// ---Event Handler Lambda'ları ile Reusable Composable ---
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text)
    }
}

// ---State Hoisting ile Parent-Child Communication ---
@Composable
fun ParentComponent() {
    var parentState by remember { mutableStateOf("") }

    Column {
        Text("Parent State: $parentState")
        ChildComponent(
            value = parentState,
            onValueChange = { parentState = it }
        )
    }
}

@Composable
fun ChildComponent(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Child Input") }
    )
}

// ---LazyColumn ile Liste State Management ---
@Composable
fun TodoList() {
    var todos by remember { mutableStateOf(listOf("Kahvaltı yap", "Çalış", "Spor yap")) }
    var newTodo by remember { mutableStateOf("") }

    Column {
        Row {
            OutlinedTextField(
                value = newTodo,
                onValueChange = { newTodo = it },
                label = { Text("Yeni görev") }
            )
            Button(
                onClick = {
                    if (newTodo.isNotBlank()) {
                        todos = todos + newTodo
                        newTodo = ""
                    }
                }
            ) {
                Text("Ekle")
            }
        }

        LazyColumn {
            items(todos) { todo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(todo)
                    IconButton(
                        onClick = { todos = todos.filter { it != todo } }
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Sil")
                    }
                }
            }
        }
    }
}

// ---Form State Management ---
@Composable
fun LoginForm() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-posta") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Şifre") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (passwordVisible) "Şifreyi gizle" else "Şifreyi göster"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotBlank() && password.isNotBlank()
        ) {
            Text("Giriş Yap")
        }
    }
}

// ---State Holder Class ile State Management ---
class FormState {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var age by mutableStateOf("")

    fun clear() {
        name = ""
        email = ""
        age = ""
    }

    fun isValid(): Boolean {
        return name.isNotBlank() && email.isNotBlank() && age.isNotBlank()
    }
}

@Composable
fun FormWithStateHolder() {
    val formState = remember { FormState() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = formState.name,
            onValueChange = { formState.name = it },
            label = { Text("İsim") }
        )

        OutlinedTextField(
            value = formState.email,
            onValueChange = { formState.email = it },
            label = { Text("E-posta") }
        )

        OutlinedTextField(
            value = formState.age,
            onValueChange = { formState.age = it },
            label = { Text("Yaş") }
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { formState.clear() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Temizle")
            }

            Button(
                onClick = { /* Submit logic */ },
                modifier = Modifier.weight(1f),
                enabled = formState.isValid()
            ) {
                Text("Gönder")
            }
        }
    }
}

// ---Sadece Gerekli Parametreleri Geçirme ---
@Composable
fun UserCard(
    name: String,
    email: String,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Text(text = email, style = MaterialTheme.typography.bodyMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onEditClick) {
                    Text("Düzenle")
                }
                TextButton(onClick = onDeleteClick) {
                    Text("Sil")
                }
            }
        }
    }
}

// --- Event Handler ile State Güncelleme ---
@Composable
fun EventHandlerExample() {
    var count by remember { mutableStateOf(0) }
    var lastAction by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Sayaç: $count")
        Text("Son işlem: $lastAction")

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    count++
                    lastAction = "Artırıldı"
                }
            ) {
                Text("Artır")
            }

            Button(
                onClick = {
                    count--
                    lastAction = "Azaltıldı"
                }
            ) {
                Text("Azalt")
            }

            Button(
                onClick = {
                    count = 0
                    lastAction = "Sıfırlandı"
                }
            ) {
                Text("Sıfırla")
            }
        }
    }
}
