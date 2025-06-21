package com.dilara.composeexample.components.bottomSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymbolSearchScreen(onBackClick: () -> Unit) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Symbol Search") },
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
                Text("Open Symbol Search Bottom Sheet")
            }
        }

        if (showBottomSheet) {
            SymbolSearchBottomSheet(onDismiss = { showBottomSheet = false })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SymbolSearchBottomSheet(onDismiss: () -> Unit) {
    var searchText by remember { mutableStateOf("") }
    var isEditMode by remember { mutableStateOf(false) }

    val previousSearches = remember {
        mutableStateListOf(
            "AFYONT",
            "A1CAP"
        )
    }

    val allCategoriesItems = remember {
        mutableStateListOf(
            CategoryItem("A1CAP", "A1 CAPITAL YATIRIM", "N", Color(0xFF42A5F5), isSelected = true),
            CategoryItem("A1YEN", "A1 YENILENEBILIR ENERJI", "X", Color(0xFF66BB6A)),
            CategoryItem("ACSEL", "ACISELSAN ACIPAYAM SELULOZ SAN.", "A", Color(0xFFEF5350)),
            CategoryItem("ADEL", "ADEL KALEMCILIK", "A", Color(0xFFFFA726), isSelected = true),
            CategoryItem("ADESE", "ADESE GAYRIMENKUL YATIRIM", "A", Color(0xFF66BB6A), isSelected = true),
            CategoryItem("ADESET", "ADESE.TE TEMERRUT", "A", Color(0xFFEF5350)),
            CategoryItem("ADGYO", "ADRA GMYO", "ADRA", Color(0xFF78909C)),
            CategoryItem("AEFES", "AEFES BIRACILIK", "EFES", Color(0xFFB0BEC5))
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = Modifier.fillMaxHeight(0.9f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
                Text(
                    text = "Sembol Arama",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { isEditMode = !isEditMode }) {
                    Text(if (isEditMode) "Tamam" else "Güncelle")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ara...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color(0xFFF0F0F0),
                    unfocusedContainerColor = Color(0xFFF0F0F0),
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // "Önceki Aramalarım" Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.History, contentDescription = "History")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Önceki Aramalarım",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 150.dp)
            ) {
                items(previousSearches) { item ->
                    ListItem(
                        headlineContent = { Text(item) },
                        leadingContent = {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color.LightGray, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.first().toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        },
                        modifier = Modifier.clickable { /* Handle item click */ }
                    )
                    Divider()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Tüm Kategoriler",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(allCategoriesItems) { item ->
                    ListItem(
                        headlineContent = { Text(item.title) },
                        supportingContent = { Text(item.subtitle) },
                        leadingContent = {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(item.color, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.iconText,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        },
                        trailingContent = {
                            if (isEditMode) {
                                Checkbox(
                                    checked = item.isSelected,
                                    onCheckedChange = { newValue ->
                                        val index = allCategoriesItems.indexOf(item)
                                        if (index != -1) {
                                            allCategoriesItems[index] = item.copy(isSelected = newValue)
                                        }
                                    }
                                )
                            } else {
                                if (item.isSelected) {
                                    Icon(imageVector = Icons.Default.Done, contentDescription = "Selected")
                                }
                            }
                        },
                        modifier = Modifier.clickable {
                            if (isEditMode) {
                                val index = allCategoriesItems.indexOf(item)
                                if (index != -1) {
                                    allCategoriesItems[index] = item.copy(isSelected = !item.isSelected)
                                }
                            }
                        }
                    )
                    Divider()
                }
            }
        }
    }
}

data class CategoryItem(
    val title: String,
    val subtitle: String,
    val iconText: String,
    val color: Color,
    var isSelected: Boolean = false
)

@Preview(showBackground = true)
@Composable
fun SymbolSearchScreenPreview() {
    MaterialTheme {
        SymbolSearchScreen(onBackClick = {})
    }
} 