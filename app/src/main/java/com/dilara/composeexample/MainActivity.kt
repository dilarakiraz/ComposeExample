package com.dilara.composeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dilara.composeexample.components.appbar.SmallTopAppBarExample
import com.dilara.composeexample.components.appbar.MediumTopAppBarExample
import com.dilara.composeexample.components.appbar.LargeTopAppBarExample
import com.dilara.composeexample.components.appbar.BottomAppBarExample

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

@Composable
fun ExamplesList() {
    val examples = listOf(
        ExampleItem("Small Top App Bar", { SmallTopAppBarExample(it) }),
        ExampleItem("Medium Top App Bar", { MediumTopAppBarExample(it) }),
        ExampleItem("Large Top App Bar", { LargeTopAppBarExample(it) }),
        ExampleItem("Bottom App Bar", { BottomAppBarExample(it) })
    )

    var selectedExample by remember { mutableStateOf<ExampleItem?>(null) }

    if (selectedExample != null) {
        selectedExample?.content?.invoke { selectedExample = null }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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

data class ExampleItem(
    val title: String,
    val content: @Composable (onBackClick: () -> Unit) -> Unit
)