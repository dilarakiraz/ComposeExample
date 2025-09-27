package com.dilara.composeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dilara.composeexample.components.appbar.SmallTopAppBarExample
import com.dilara.composeexample.components.appbar.MediumTopAppBarExample
import com.dilara.composeexample.components.appbar.LargeTopAppBarExample
import com.dilara.composeexample.components.appbar.BottomAppBarExample
import com.dilara.composeexample.components.bottomSheets.StandardModalBottomSheet
import com.dilara.composeexample.components.bottomSheets.ListBottomSheet
import com.dilara.composeexample.components.bottomSheets.SymbolSearchScreen
import com.dilara.composeexample.applayout.LayoutBasicsPreview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExamplesList()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamplesList() {
    val examples = listOf(
        ExampleItem("Layout Basics", { LayoutBasicsPreview() }),
        ExampleItem("Small Top App Bar", { SmallTopAppBarExample(it) }),
        ExampleItem("Medium Top App Bar", { MediumTopAppBarExample(it) }),
        ExampleItem("Large Top App Bar", { LargeTopAppBarExample(it) }),
        ExampleItem("Bottom App Bar", { BottomAppBarExample(it) }),
        ExampleItem("Standard Modal Bottom Sheet", { StandardModalBottomSheet(it) }),
        ExampleItem("Bottom Sheet with List", { ListBottomSheet(it) })
    )

    var selectedExample by remember { mutableStateOf<ExampleItem?>(null) }
    var showSymbolSearchSheet by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = "",
            onValueChange = { /* Do nothing */ },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { showSymbolSearchSheet = true },
            placeholder = { Text("Sembol Ara...") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
            singleLine = true
        )

        if (selectedExample != null) {
            selectedExample?.content?.invoke { selectedExample = null }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(examples) { example ->
                    Button(
                        onClick = { selectedExample = example },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(example.title)
                    }
                }
            }
        }
    }

    if (showSymbolSearchSheet) {
        SymbolSearchScreen(onBackClick = { showSymbolSearchSheet = false })
    }
}

data class ExampleItem(
    val title: String,
    val content: @Composable (onBackClick: () -> Unit) -> Unit
)