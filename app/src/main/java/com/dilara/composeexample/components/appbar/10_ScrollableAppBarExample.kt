package com.dilara.composeexample.components.appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollableAppBarExample(onBackClick: () -> Unit = {}) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text("Scrollable AppBar")
                        Text("Scroll to see the effect", style = MaterialTheme.typography.bodyMedium)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {/*todo*/ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(50) { index ->
                ListItem(
                    headlineContent = { Text("Item $index") },
                    supportingContent = { Text("Description for item $index") },
                    leadingContent = {
                        Icon(Icons.Default.Star, contentDescription = null)
                    }
                )
                Divider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScrollableAppBarExamplePreview() {
    ScrollableAppBarExample()
}
