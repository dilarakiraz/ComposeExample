package com.dilara.composeexample.architecture

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * # Jetpack Compose Architectural Layering
 *
 * Jetpack Compose, tek bir monolitik proje değil, birbirinin üzerine inşa edilen
 * modüllerden oluşan katmanlı bir mimaridir. Bu katmanlar, daha yüksek seviyeli
 * bileşenler oluşturmak için işlevselliği birleştirir.
 *
 * ## Katmanlar (Aşağıdan Yukarıya)
 *
 * ### 1. Runtime Layer (En Alt Katman)
 * - Compose runtime'ının temellerini sağlar
 * - remember, mutableStateOf, @Composable annotation, SideEffect
 * - Sadece tree management yetenekleri gerekirse bu katmanı kullan
 * - UI'ya ihtiyaç yoksa bu katman yeterli
 *
 * ### 2. UI Layer
 * - ui-text, ui-graphics, ui-tooling gibi modüllerden oluşur
 * - LayoutNode, Modifier, input handlers, custom layouts, drawing
 * - Temel UI toolkit kavramları gerekirse bu katmanı kullan
 * - Foundation ve Material'dan bağımsız çalışır
 *
 * ### 3. Foundation Layer
 * - Design system'dan bağımsız building block'lar
 * - Row, Column, LazyColumn, gesture recognition
 * - Kendi design system'ını oluşturmak için kullan
 * - Material Design'a bağlı değil
 *
 * ### 4. Material Layer (En Üst Katman)
 * - Material Design system implementasyonu
 * - Theming system, styled components, ripple effects, icons
 * - Material Design kullanıyorsan bu katmanı kullan
 * - En yüksek seviye abstraction
 *
 * ## Tasarım Prensipleri
 *
 * ### 1. Kontrol
 * - Yüksek seviye bileşenler daha fazla şey yapar ama kontrolü sınırlar
 * - Daha fazla kontrol için alt katmana "drop down" yapabilirsin
 * - animateColorAsState vs Animatable örneği
 *
 * ### 2. Özelleştirme (Customization)
 * - Küçük building block'ları birleştirerek özelleştirme kolaylaşır
 * - Button'un 4 bileşenden oluşması örneği
 * - Parametrelerle sınırlı kalırsan alt katmana in
 *
 * ### 3. Doğru Abstraction Seçimi
 * - En yüksek seviye bileşeni tercih et
 * - Accessibility ve best practice'ler dahil
 * - Modifier.pointerInput yerine Modifier.draggable kullan
 *
 * ## Katmanlar Arası Geçiş
 * - Her katman alt katmanın public API'lerini kullanır
 * - Modül sınırları doğrulanır
 * - İhtiyaç halinde herhangi bir katman değiştirilebilir
 * - En basit isimler en yüksek seviye bileşenler için ayrılmış
 *
 * **Örnek:** androidx.compose.material.Text
 *        ↓
 *        androidx.compose.foundation.text.BasicText
 *
 * ## Dikkat Edilecek Noktalar
 * - Alt katmana inerken functionality kaybetme
 * - Accessibility desteğini ihmal etme
 * - Fork ettiğin bileşenin güncellemelerini kaçırma
 * - En yüksek seviye bileşeni tercih et
 */

// ============================================================================
// 1. RUNTIME LAYER ÖRNEKLERİ
// ============================================================================

// --- Temel Runtime Kavramları ---
@Composable
fun RuntimeLayerExample() {
    // remember: Composition'da değeri hatırlar
    var counter by remember { mutableStateOf(0) }

    // SideEffect: Composition dışındaki değişiklikleri yönetir
    SideEffect {
        println("Counter değişti: $counter")
    }

    // @Composable annotation: Bu fonksiyon Compose tree'sine katılır
    Column {
        Text("Sayaç: $counter")
        Button(onClick = { counter++ }) {
            Text("Artır")
        }
    }
}

// --- State Management ile Runtime ---
@Composable
fun StateManagementExample() {
    // mutableStateOf: Observable state oluşturur
    var name by remember { mutableStateOf("") }
    var age by remember { mutableIntStateOf(0) }

    // State değişiklikleri otomatik recomposition tetikler
    Column {
        Text("İsim: $name")
        Text("Yaş: $age")

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("İsim") }
        )

        Button(onClick = { age++ }) {
            Text("Yaş Artır")
        }
    }
}

// --- SideEffect Kullanımı ---
@Composable
fun SideEffectExample2() {
    var isVisible by remember { mutableStateOf(false) }

    // LaunchedEffect: Composition'a bağlı side effect
    LaunchedEffect(isVisible) {
        if (isVisible) {
            println("Component görünür hale geldi")
        }
    }

    // DisposableEffect: Cleanup gerektiren side effect
    DisposableEffect(Unit) {
        println("Component oluşturuldu")
        onDispose {
            println("Component temizlendi")
        }
    }

    Column {
        Button(onClick = { isVisible = !isVisible }) {
            Text(if (isVisible) "Gizle" else "Göster")
        }

        if (isVisible) {
            Text("Görünür içerik")
        }
    }
}

// ============================================================================
// 2. UI LAYER ÖRNEKLERİ
// ============================================================================

// --- Custom Layout ile UI Layer ---
@Composable
fun CustomLayoutExample() {
    // Layout: UI layer'ın temel layout bileşeni
    Layout(
        content = {
            Box(
                Modifier
                    .size(50.dp)
                    .background(Color.Red)
            )
            Box(
                Modifier
                    .size(50.dp)
                    .background(Color.Green)
            )
            Box(
                Modifier
                    .size(50.dp)
                    .background(Color.Blue)
            )
        }
    ) { measurables, constraints ->
        // Custom layout logic
        val placeables = measurables.map { it.measure(constraints) }
        val width = placeables.sumOf { it.width }
        val height = placeables.maxOfOrNull { it.height } ?: 0

        layout(width, height) {
            var x = 0
            placeables.forEach { placeable ->
                placeable.place(x, 0)
                x += placeable.width
            }
        }
    }
}

// --- Modifier ile UI Layer ---
@Composable
fun ModifierExample() {
    // Modifier: UI layer'ın temel styling ve behavior aracı
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color(0xFFADD8E6))
            .clip(RoundedCornerShape(16.dp))
            .clickable { println("Tıklandı") }
    ) {
        Text(
            "Custom Box",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

// --- Graphics ile UI Layer ---
@Composable
fun GraphicsExample() {
    // graphicsLayer: UI layer'ın graphics yetenekleri
    Box(
        modifier = Modifier
            .size(100.dp)
            .graphicsLayer {
                rotationZ = 45f
                scaleX = 1.5f
                scaleY = 1.5f
                alpha = 0.7f
            }
            .background(Color.Magenta)
    ) {
        Text("Döndürülmüş", modifier = Modifier.align(Alignment.Center))
    }
}

// ============================================================================
// 3. FOUNDATION LAYER ÖRNEKLERİ
// ============================================================================

// --- Row ve Column ile Foundation ---
@Composable
fun FoundationLayoutExample() {
    // Row: Yatay düzenleme (Foundation layer)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Sol")
        Text("Orta")
        Text("Sağ")
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Column: Dikey düzenleme (Foundation layer)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Üst")
        Text("Orta")
        Text("Alt")
    }
}

// --- LazyColumn ile Foundation ---
@Composable
fun LazyColumnExample() {
    val items = remember { List(100) { "Item $it" } }

    // LazyColumn: Performanslı liste (Foundation layer)
    LazyColumn(
        modifier = Modifier.height(200.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(items) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

// --- Gesture Recognition ile Foundation ---
@Composable
fun GestureExample() {
    var offset by remember { mutableStateOf(0f) }

    // draggable: Foundation layer'ın gesture recognition'ı
    Box(
        modifier = Modifier
            .size(100.dp)
            .offset { IntOffset(offset.toInt(), 0) }
            .background(Color.Cyan)
            .draggable(
                state = rememberDraggableState { delta ->
                    offset += delta
                },
                orientation = androidx.compose.foundation.gestures.Orientation.Horizontal
            )
    ) {
        Text("Sürükle", modifier = Modifier.align(Alignment.Center))
    }
}

// --- Scrollable ile Foundation ---
@Composable
fun ScrollableExample() {
    val scrollState = rememberScrollState()

    // scrollable: Foundation layer'ın scroll yeteneği
    Column(
        modifier = Modifier
            .height(200.dp)
            .scrollable(
                state = scrollState,
                orientation = androidx.compose.foundation.gestures.Orientation.Vertical
            )
    ) {
        repeat(20) { index ->
            Text(
                "Scrollable Item $index",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

// ============================================================================
// 4. MATERIAL LAYER ÖRNEKLERİ
// ============================================================================

// --- Material Theming ---
@Composable
fun MaterialThemingExample() {
    // MaterialTheme: Material layer'ın theming sistemi
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color.Red,
            secondary = Color.Blue,
            surface = Color(0xFFF5F5F5)
        ),
        typography = MaterialTheme.typography
    ) {
        Column {
            Text("Primary Renk", style = MaterialTheme.typography.headlineLarge)
            Text("Body Text", style = MaterialTheme.typography.bodyLarge)

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Material Button")
            }
        }
    }
}

// --- Material Components ---
@Composable
fun MaterialComponentsExample() {
    var text by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

    Column {
        // TextField: Material layer'ın styled component'i
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Material TextField") },
            leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox: Material layer'ın styled component'i
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it }
            )
            Text("Material Checkbox")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card: Material layer'ın styled component'i
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Material Card")
                Text("Ripple effect ve elevation ile")
            }
        }
    }
}

// --- Ripple Effects ---
@Composable
fun RippleEffectExample() {
    // Ripple: Material layer'ın ripple indication'ı
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Blue)
            .clickable { }
    ) {
        Text("Ripple Effect", modifier = Modifier.align(Alignment.Center))
    }
}

// ============================================================================
// 5. KATMANLAR ARASI GEÇİŞ ÖRNEKLERİ
// ============================================================================

// --- Yüksek Seviye vs Düşük Seviye Animation ---
@Composable
fun AnimationLevelsExample() {
    var isExpanded by remember { mutableStateOf(false) }

    // YÜKSEK SEVİYE: animateColorAsState (Material layer)
    val highLevelColor by animateColorAsState(
        targetValue = if (isExpanded) Color.Green else Color.Red,
        animationSpec = tween(1000)
    )

    // DÜŞÜK SEVİYE: Animatable (Runtime layer) - Color için
    var lowLevelColor by remember { mutableStateOf(Color.Gray) }

    LaunchedEffect(isExpanded) {
        // Basit state değişikliği
        lowLevelColor = if (isExpanded) Color.Blue else Color.Yellow
    }

    Column {
        Button(onClick = { isExpanded = !isExpanded }) {
            Text("Toggle")
        }

        // Yüksek seviye animation
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(highLevelColor)
        ) {
            Text("High Level", modifier = Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Düşük seviye animation
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(lowLevelColor)
        ) {
            Text("Low Level", modifier = Modifier.align(Alignment.Center))
        }
    }
}

// --- Component Customization Örneği ---
@Composable
fun ComponentCustomizationExample() {
    // ORİJİNAL Material Button
    Button(onClick = { }) {
        Text("Orijinal Button")
    }

    Spacer(modifier = Modifier.height(16.dp))

    // ÖZELLEŞTİRİLMİŞ Gradient Button (Foundation + Material)
    GradientButton(
        background = listOf(Color.Red, Color.Blue),
        onClick = { }
    ) {
        Text("Gradient Button")
    }

    Spacer(modifier = Modifier.height(16.dp))

    // TAMAMEN ÖZEL Button (Sadece Foundation)
    BespokeButton(
        backgroundColor = Color.Green,
        onClick = { }
    ) {
        Text("Bespoke Button")
    }
}

// --- Gradient Button (Material + Foundation) ---
@Composable
fun GradientButton(
    background: List<Color>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    // Foundation layer'dan Row kullan
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(
                Brush.horizontalGradient(background)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Material layer'dan typography kullan
        ProvideTextStyle(MaterialTheme.typography.labelLarge) {
            content()
        }
    }
}

// --- Bespoke Button (Sadece Foundation) ---
@Composable
fun BespokeButton(
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    // Sadece Foundation layer kullan
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(backgroundColor)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Material layer kullanmadan custom text style
        Text(
            text = "Custom Text",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ============================================================================
// 6. ABSTRACTION SEVİYE ÖRNEKLERİ
// ============================================================================

// --- Gesture Handling Seviyeleri ---
@Composable
fun GestureLevelsExample() {
    var offset by remember { mutableStateOf(0f) }

    Column {
        Text("Gesture Handling Seviyeleri")

        Spacer(modifier = Modifier.height(16.dp))

        // EN YÜKSEK SEVİYE: Modifier.draggable (Foundation)
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset { IntOffset(offset.toInt(), 0) }
                .background(Color.Red)
                .draggable(
                    state = rememberDraggableState { delta ->
                        offset += delta
                    },
                    orientation = androidx.compose.foundation.gestures.Orientation.Horizontal
                )
        ) {
            Text("Draggable", modifier = Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ORTA SEVİYE: Modifier.scrollable (Foundation)
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Green)
                .scrollable(
                    state = rememberScrollState(),
                    orientation = androidx.compose.foundation.gestures.Orientation.Vertical
                )
        ) {
            Text("Scrollable", modifier = Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // EN DÜŞÜK SEVİYE: Modifier.pointerInput (UI)
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Blue)
                .pointerInput(Unit) {
                    // Custom gesture handling
                }
        ) {
            Text("Pointer Input", modifier = Modifier.align(Alignment.Center))
        }
    }
}

// --- Text Component Seviyeleri ---
@Composable
fun TextLevelsExample() {
    Column {
        // EN YÜKSEK SEVİYE: Material Text
        Text(
            text = "Material Text",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ORTA SEVİYE: Foundation BasicText
        BasicText(
            text = "Foundation BasicText",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // EN DÜŞÜK SEVİYE: UI Layer ile Custom Text
        CustomText(
            text = "Custom UI Text",
            color = Color.Red,
            fontSize = 20.sp
        )
    }
}

// --- Custom Text (UI Layer) ---
@Composable
fun CustomText(
    text: String,
    color: Color,
    fontSize: androidx.compose.ui.unit.TextUnit
) {
    // UI layer'ın temel text rendering
    BasicText(
        text = text,
        style = TextStyle(
            color = color,
            fontSize = fontSize,
            textDecoration = TextDecoration.Underline
        )
    )
}

// ============================================================================
// 7. PERFORMANCE VE BEST PRACTICES
// ============================================================================

// --- Katman Seçimi Best Practices ---
@Composable
fun BestPracticesExample() {
    Column {
        Text("Best Practices Örnekleri")

        Spacer(modifier = Modifier.height(16.dp))

        //  DOĞRU: En yüksek seviye bileşeni kullan
        Button(onClick = { }) {
            Text("Material Button")
        }

        Spacer(modifier = Modifier.height(8.dp))

        //  YANLIŞ: Gereksiz yere düşük seviye kullanma
        // Modifier.pointerInput ile button yapmak yerine
        // Material Button kullan

        Spacer(modifier = Modifier.height(16.dp))

        //  DOĞRU: Accessibility desteği olan bileşen
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Accessible TextField") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        //  YANLIŞ: Accessibility desteği olmayan custom input
        // BasicText ile input yapmak yerine
        // OutlinedTextField kullan
    }
}

// --- Modül Bağımlılık Optimizasyonu ---
@Composable
fun DependencyOptimizationExample() {
    // Sadece Foundation layer gerekirse
    // Material dependency'si ekleme

    Column {
        // Foundation layer bileşenleri
        Row {
            Text("Foundation Row")
        }

        Column {
            Text("Foundation Column")
        }

        // LazyColumn da Foundation layer
        LazyColumn {
            items(5) { index ->
                Text("Item $index")
            }
        }
    }
}

// ============================================================================
// 8. ÖRNEKLER
// ============================================================================


// --- Chat Uygulaması Katmanları ---
@Composable
fun ChatLayersExample() {
    // Runtime layer: State management
    var messages by remember { mutableStateOf(listOf("Merhaba", "Nasılsın?")) }
    var newMessage by remember { mutableStateOf("") }

    Column {
        // Foundation layer: LazyColumn
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                // Material layer: Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = message,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // Foundation layer: Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Material layer: OutlinedTextField
            OutlinedTextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                label = { Text("Mesaj") },
                modifier = Modifier.weight(1f)
            )

            // Material layer: Button
            Button(
                onClick = {
                    if (newMessage.isNotBlank()) {
                        messages = messages + newMessage
                        newMessage = ""
                    }
                }
            ) {
                Text("Gönder")
            }
        }
    }
}
