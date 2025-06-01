package com.dilara.composeexample.components.appbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dilara.composeexample.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedSearchAppBarExample() {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var showFilters by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedDate by remember { mutableStateOf<String?>(null) }
    var selectedSort by remember { mutableStateOf<String?>(null) }

    val searchHistory = remember { mutableStateListOf("Android", "Kotlin", "Compose", "Material3") }

    val categories = listOf("Tümü", "Geliştirme", "Tasarım", "İş", "Eğitim")

    val dateOptions = listOf("Bugün", "Bu Hafta", "Bu Ay", "Bu Yıl", "Tüm Zamanlar")

    val sortOptions = listOf("En Yeni", "En Popüler", "En Çok Beğenilen", "En Çok Yorum Alan")

    val searchResults = remember(searchQuery, selectedCategory, selectedDate, selectedSort) {
        if (searchQuery.isEmpty()) {
            emptyList()
        } else {
            listOf(
                "Android Studio Tutorial",
                "Kotlin Coroutines Guide",
                "Jetpack Compose Basics",
                "Material Design 3 Implementation",
                "Android Development Tips",
                "Compose Navigation",
                "State Management in Compose",
                "Compose UI Components"
            ).filter {
                it.contains(searchQuery, ignoreCase = true) &&
                        (selectedCategory == null || selectedCategory == "Tümü" || it.contains(selectedCategory!!, ignoreCase = true))
            }
        }
    }

    Scaffold(
        topBar = {
            if (isSearchActive) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = { /* Arama işlemi */ },
                    active = isSearchActive,
                    onActiveChange = { isSearchActive = it },
                    placeholder = { Text("Ara...") },
                    leadingIcon = {
                        IconButton(onClick = { isSearchActive = false }) {
                            Icon(Icons.Default.ArrowBack, "Geri")
                        }
                    },
                    trailingIcon = {
                        Row {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, "Temizle")
                                }
                            }
                            IconButton(onClick = { showFilters = !showFilters }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_filter_list),
                                    contentDescription = "Filtrele"
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyColumn {
                        if (searchQuery.isEmpty()) {
                            item {
                                Text(
                                    "Arama Geçmişi",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                            items(searchHistory) { historyItem ->
                                ListItem(
                                    headlineContent = { Text(historyItem) },
                                    leadingContent = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_history),
                                            contentDescription = null
                                        )
                                    },
                                    trailingContent = {
                                        IconButton(onClick = { searchHistory.remove(historyItem) }) {
                                            Icon(Icons.Default.Close, contentDescription = "Kaldır")
                                        }
                                    },
                                    modifier = Modifier.clickable { searchQuery = historyItem }
                                )
                            }
                        } else {
                            items(searchResults) { result ->
                                ListItem(
                                    headlineContent = { Text(result) },
                                    leadingContent = {
                                        Icon(Icons.Default.Search, contentDescription = null)
                                    },
                                    trailingContent = {
                                        IconButton(onClick = { }) {
                                            Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorilere Ekle")
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            } else {
                TopAppBar(
                    title = { Text("Gelişmiş Arama") },
                    actions = {
                        IconButton(onClick = { isSearchActive = true }) {
                            Icon(Icons.Default.Search, "Ara")
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            AnimatedVisibility(
                visible = showFilters,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Filtreler",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "Kategori",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            categories.forEach { category ->
                                FilterChip(
                                    selected = category == selectedCategory,
                                    onClick = {
                                        selectedCategory = if (selectedCategory == category) null else category
                                    },
                                    label = { Text(category) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "Tarih",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            dateOptions.forEach { date ->
                                FilterChip(
                                    selected = date == selectedDate,
                                    onClick = {
                                        selectedDate = if (selectedDate == date) null else date
                                    },
                                    label = { Text(date) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "Sıralama",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            sortOptions.forEach { sort ->
                                FilterChip(
                                    selected = sort == selectedSort,
                                    onClick = {
                                        selectedSort = if (selectedSort == sort) null else sort
                                    },
                                    label = { Text(sort) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                selectedCategory = null
                                selectedDate = null
                                selectedSort = null
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Filtreleri Sıfırla")
                        }
                    }
                }
            }

            if (!isSearchActive) {
                Text(
                    "Arama yapmak için üstteki arama ikonuna tıklayın",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdvancedSearchAppBarExamplePreview() {
    MaterialTheme {
        AdvancedSearchAppBarExample()
    }
} 