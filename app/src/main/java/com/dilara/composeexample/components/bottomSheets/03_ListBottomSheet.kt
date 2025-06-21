package com.dilara.composeexample.components.bottomSheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListBottomSheet(onBackClick: () -> Unit) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val items = remember {
        List(20) { "Item ${it + 1}" }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bottom Sheet with List") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { showBottomSheet = true }
            ) {
                Text("Show Bottom Sheet with List")
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Bottom Sheet with List",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(items) { item ->
                            ListItem(
                                headlineContent = { Text(item) },
                                modifier = Modifier.clickable { /* Handle item click */ }
                            )
                            Divider()
                        }
                    }
                    
                    Button(
                        onClick = { showBottomSheet = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListBottomSheetPreview() {
    MaterialTheme {
        ListBottomSheet(onBackClick = {})
    }
} 