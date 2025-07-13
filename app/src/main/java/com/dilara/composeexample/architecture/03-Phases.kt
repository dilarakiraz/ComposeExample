package com.dilara.composeexample.architecture

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * ============================================================================
 * 🟦 Jetpack Compose Phases (Frame Rendering Aşamaları)
 * ============================================================================
 * Compose, her frame'i üç ana fazda işler:
 *
 * 1-Composition: Hangi UI gösterilecek? (Composable fonksiyonlar çalışır, UI ağacı kurulur)
 * 2-Layout: Nerede gösterilecek? (Her node'un boyutu ve konumu belirlenir)
 * 3-Drawing: Nasıl çizilecek? (Her node ekrana çizilir)
 *
 * Bu fazlar, Compose'un performanslı ve reaktif olmasını sağlar. State değişimlerinde sadece gerekli fazlar tetiklenir.
 *
 */

// -----------------------------------------------------------------------------
/**
 * 1- Composition Phase
 * -----------------------------------------------------------------------------
 * Amaç: Hangi UI’nın gösterileceğine karar verilir. Yani Compose, composable fonksiyonları çalıştırır ve bir UI ağacı (tree) oluşturur.
 * State Okuması: Eğer bir state burada okunursa, o state değiştiğinde ilgili composable fonksiyon tekrar çalışır (recomposition olur).
 * Ne zaman tetiklenir? Sadece UI’nın yapısı (gösterilecek widget’lar, dallanma, koşul vs.) değişirse bu faz tekrar çalışır.
 *
 * Pratik: State'i burada okumak, UI'nın yeniden kurulmasına sebep olur.
 */
@Composable
fun CompositionPhaseExample() {
    var showDetails by remember { mutableStateOf(false) } // State burada tanımlanıyor ve okunuyor
    Column(Modifier.padding(16.dp)) {
        Text("Stock: AKBNK", fontSize = 20.sp)
        if (showDetails) {  //State burada okunuyor, true/false'a göre UI değişiyor
            Text("Price: 36.2₺", color = Color.Gray)
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = if (showDetails) "Hide Details" else "Show Details",
            color = Color.Blue,
            modifier = Modifier
                .background(Color(0xFFE3F2FD))
                .padding(8.dp)
                .clickable { showDetails = !showDetails } //Tıklanınca state değişiyor, recomposition tetikleniyor
        )
    }
}
// State burada okunuyor, showDetails değişirse sadece bu composable recompose olur.

// -----------------------------------------------------------------------------
/**
 *2- Layout Phase
 * -----------------------------------------------------------------------------
 * - UI ağacındaki her node'un boyutu ve konumu belirlenir.
 * - State okuması burada yapılırsa, sadece layout ve drawing fazı tekrar çalışır (daha verimli!).
 * - Özellikle scroll, offset, animasyon gibi değerler için state'i layout fazında okumak performans kazandırır.
 *
 * Pratik: Offset, scroll gibi değerleri layout fazında okumak için lambda kullanan Modifier'ları tercih et.
 */
@Composable
fun LayoutPhaseOptimizedExample() {
    val listState = rememberLazyListState()
    Box(Modifier.height(200.dp)) {
        // Görseli, scroll offset'e göre yukarı kaydır (parallax)
        Image(
            painter = painterResource(android.R.drawable.ic_menu_gallery),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .offset {
                    // State burada layout fazında okunur, sadece layout ve draw tetiklenir
                    IntOffset(
                        x = 0,
                        y = -listState.firstVisibleItemScrollOffset / 2
                    ) //Lambda, layout sırasında çalışır ve döndürdüğün IntOffset değeri, görselin ekrandaki konumunu belirler.
                    //Burada state okursan, sadece layout ve drawing fazı tekrar çalışır, gereksiz recomposition olmaz.
                },
            contentScale = ContentScale.Crop
        )
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            items(30) { idx ->
                Text("Item $idx", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
//Eğer offset(y = ...) gibi doğrudan değer verirsen, state composition phase’de okunur.
//Eğer offset { ... } ile lambda verirsen, state layout phase’de okunur.
// Parallax efekti için offset lambda kullanmak, gereksiz recomposition'ı önler.

// -----------------------------------------------------------------------------
/**
 * 3- Drawing Phase
 * -----------------------------------------------------------------------------
 * - Her node, ekrana çizilir (Canvas, Modifier.drawBehind, Modifier.drawWithContent).
 * - State okuması burada yapılırsa, sadece draw fazı tekrar çalışır (en verimli!).
 *
 * Pratik: Sık değişen, sadece görsel efekt olan state'leri draw fazında oku.
 */
@Composable
fun DrawingPhaseExample() {
    var color by remember { mutableStateOf(Color.Red) }
    Box(
        Modifier
            .size(120.dp)
            .background(Color.LightGray)
            .drawBehind {
                // State burada okunuyor, color değişirse sadece draw fazı tekrar çalışır
                drawCircle(color, radius = size.minDimension / 2)
            }
            .clickable { color = if (color == Color.Red) Color.Green else Color.Red }
    )
}
// drawBehind ile sadece renk değişiminde draw fazı tetiklenir, UI'nın geri kalanı etkilenmez.

// -----------------------------------------------------------------------------
/**
 * -----------------------------------------------------------------------------
 * - State'i mümkün olduğunca geç fazda (draw > layout > composition) oku. Örneğin, sadece görsel efekt için değişen bir state'i draw fazında okursan, tüm UI yerine sadece çizim güncellenir.
 * - Modifier.offset, Modifier.padding, Modifier.height gibi lambda alan versiyonları kullanmak, state'in layout fazında okunmasını sağlar ve gereksiz recomposition'ı önler.
 *
 *   // Yanlış: State doğrudan okunuyor (composition phase)
 *   Modifier.offset(y = scrollOffset)
 *
 *   // Doğru: State lambda ile okunuyor (layout phase)
 *   Modifier.offset { IntOffset(0, scrollOffset) }
 *
 * - Sık değişen state'ler için derivedStateOf kullanarak gereksiz recomposition'ı engelle:
 *
 *   // Yanlış: Her scroll değişiminde tüm composable recompose olur
 *   val isAtTop = listState.firstVisibleItemIndex == 0
 *
 *   // Doğru: derivedStateOf ile sadece gerçekten değişince tetiklenir
 *   val isAtTop by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
 *
 * - onSizeChanged, onGloballyPositioned gibi callback'lerde state güncellerken dikkatli ol:
 *   - Bu pattern, state güncellemesiyle tekrar callback tetiklenirse sonsuz döngüye (recomposition loop) yol açabilir!
 *   - Mümkünse custom layout veya parent koordinasyonu ile state güncelle.
 *
 *   // Yanlış: onSizeChanged içinde doğrudan state güncellemek
 *   Box(Modifier.onSizeChanged { size -> myState = size.width })
 *
 *   // Doğru: State'i parent composable'da yönetmek veya side-effect ile güncellemek
 *
 * - State'i composition fazında okuman gerekiyorsa, derivedStateOf veya snapshotFlow ile filtrele. Özellikle animasyon, scroll gibi hızlı değişen değerlerde recomposition'ı minimize et.
 *
 * - Compose, sadece gerekli fazları tekrar çalıştırır; state'in hangi fazda okunduğu çok önemlidir! Fazı optimize ederek hem performans hem de enerji verimliliği kazanırsın.
 *
 *   Kural: State'i ne kadar geç fazda okursan, o kadar az iş yapılır ve UI daha akıcı olur.
 */