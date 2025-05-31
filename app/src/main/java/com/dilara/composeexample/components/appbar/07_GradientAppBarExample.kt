package com.dilara.composeexample.components.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradientAppBarExample(onBackClick: () -> Unit = {}) {
    val gradientColors = listOf(
        Color(0xFF6200EE),
        Color(0xFF03DAC6)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Gradient AppBar",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.background(
                    brush = Brush.horizontalGradient(gradientColors)
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            Text("This example shows a gradient AppBar with:")
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Nur")
            Text("• Yavuz Selim")
            Text("• Dilara Kiraz")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GradientAppBarExamplePreview() {
    GradientAppBarExample()
}
