package com.dilara.composeexample.components.bottomSheets

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomHeightModalBottomSheet(onBackClick: () -> Unit) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            sheetState.show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Custom Height Modal BottomSheet") },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { showBottomSheet = !showBottomSheet }
                ) {
                    Text(
                        if (!showBottomSheet)
                            "Show Custom Height Bottom Sheet"
                        else
                            "Hide Custom Height Bottom Sheet"
                    )
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState,
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    dragHandle = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(4.dp),
                                shape = MaterialTheme.shapes.small,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                            ) {}
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .height(screenHeight * 0.75f)
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Custom Height Bottom Sheet",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "This bottom sheet opens at 3/4 screen height. Drag up to expand to full screen, drag down to close.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        repeat(10) { index ->
                            Text(
                                text = "Additional content line ${index + 1}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        Button(
                            onClick = { showBottomSheet = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Close")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomHeightModalBottomSheetPreview() {
    MaterialTheme {
        CustomHeightModalBottomSheet(onBackClick = {})
    }
} 