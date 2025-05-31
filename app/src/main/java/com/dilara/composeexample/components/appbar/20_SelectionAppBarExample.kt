package com.dilara.composeexample.components.appbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionAppBarExample(
    onBackClick: () -> Unit = {}
) {
    var isSelectionMode by remember { mutableStateOf(false) }
    var selectedItems by remember { mutableStateOf(0) }
    val totalItems = 10

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = isSelectionMode,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "$selectedItems selected",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "of $totalItems",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            isSelectionMode = false
                            selectedItems = 0
                        }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Cancel"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                selectedItems = if (selectedItems == totalItems) 0 else totalItems
                            }
                        ) {
                            Icon(
                                if (selectedItems == totalItems) Icons.Default.Check else Icons.Default.Add,
                                contentDescription = if (selectedItems == totalItems) "Deselect All" else "Select All"
                            )
                        }

                        IconButton(
                            onClick = { },
                            enabled = selectedItems > 0
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }

                        IconButton(
                            onClick = { },
                            enabled = selectedItems > 0
                        ) {
                            Icon(
                                Icons.Default.Share,
                                contentDescription = "Share"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
            }

            AnimatedVisibility(
                visible = !isSelectionMode,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                TopAppBar(
                    title = {
                        Text(
                            "Selection Bar",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { isSelectionMode = true }) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Select"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            Text("This example shows a Selection AppBar with:")
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Multi-select functionality")
            Text("• Select all/deselect all")
            Text("• Selection count display")
            Text("• Animated transitions")

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { selectedItems++ },
                    enabled = isSelectionMode && selectedItems < totalItems
                ) {
                    Text("Select Item")
                }

                Button(
                    onClick = {
                        if (selectedItems > 0) selectedItems--
                    },
                    enabled = isSelectionMode && selectedItems > 0
                ) {
                    Text("Deselect Item")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionAppBarExamplePreview() {
    SelectionAppBarExample()
}
