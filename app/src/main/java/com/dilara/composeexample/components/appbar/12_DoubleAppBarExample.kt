package com.dilara.composeexample.components.appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoubleAppBarExample(onBackClick: () -> Unit = {}) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Favorites", "Recent")

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Double AppBar") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceDim,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.Magenta,
                        actionIconContentColor = Color.Cyan
                    )
                )

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            Text("This example shows a double AppBar with:")
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Primary AppBar with actions")
            Text("• Secondary AppBar with tabs")
            Text("• Different colors for each AppBar")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Selected tab: ${tabs[selectedTabIndex]}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoubleAppBarExamplePreview() {
    DoubleAppBarExample()
}
