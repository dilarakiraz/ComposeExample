package com.dilara.composeexample.components.floatingActionButton

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dilara.composeexample.ui.theme.ComposeExampleTheme

@Composable
fun FabWithStockSearchAndChipFilterExample() {
    var showFilters by remember { mutableStateOf(false) }
    val chipOptions = listOf("Tümü", "Banka", "Teknoloji", "Enerji", "Gıda", "Savunma", "Perakende")
    var selectedChip by remember { mutableStateOf(chipOptions[0]) }
    var searchText by remember { mutableStateOf("") }
    val recentSearches = remember { mutableStateListOf("AKBNK", "THYAO", "SISE") }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = showFilters,
            enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(350)),
            exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(350))
        ) {
            Surface(
                color = Color.White,
                shadowElevation = 18.dp,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = { Text("Hisse Ara") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        chipOptions.forEach { option ->
                            FilterChip(
                                selected = selectedChip == option,
                                onClick = { selectedChip = option },
                                label = { Text(option) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF4F8CFF),
                                    selectedLabelColor = Color.White,
                                    containerColor = Color(0xFFF2F6FF),
                                    labelColor = Color(0xFF4F8CFF)
                                )
                            )
                        }
                    }
                    if (recentSearches.isNotEmpty()) {
                        Text("Son Aramalar", color = Color.Gray, fontSize = 15.sp)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            recentSearches.forEach { item ->
                                AssistChip(
                                    onClick = { searchText = item },
                                    label = { Text(item) },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = Color(0xFF4F8CFF).copy(alpha = 0.10f),
                                        labelColor = Color(0xFF4F8CFF)
                                    )
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { /* Filtre uygula */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F8CFF))
                        ) {
                            Text("Filtrele", color = Color.White)
                        }
                        OutlinedButton(
                            onClick = {
                                selectedChip = chipOptions[0]
                                searchText = ""
                                recentSearches.clear()
                            }
                        ) {
                            Text("Temizle")
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { showFilters = !showFilters },
            containerColor = Color(0xFF4F8CFF),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp)
                .size(68.dp)
                .shadow(16.dp, shape = MaterialTheme.shapes.extraLarge)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Filtrele", modifier = Modifier.size(36.dp))
        }
    }
}

@Preview(showBackground = true, name = "FAB Stock Search Chip Filter Modern Preview")
@Composable
fun PreviewFabWithStockSearchAndChipFilterExample() {
    ComposeExampleTheme {
        Box(Modifier.fillMaxSize()) {
            FabWithStockSearchAndChipFilterExample()
        }
    }
} 