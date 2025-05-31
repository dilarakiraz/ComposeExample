package com.dilara.composeexample.components.appbar

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorfulAppBarExample(
    onBackClick: () -> Unit = {}
) {
    var selectedColor by remember { mutableStateOf(0) }
    val colors = listOf(
        Color(0xFF2196F3),
        Color(0xFFE91E63),
        Color(0xFF9C27B0),
        Color(0xFF4CAF50),
        Color(0xFFFF9800)
    )

    val currentColor by animateColorAsState(
        targetValue = colors[selectedColor],
        animationSpec = tween(durationMillis = 500)
    )

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            "Colorful AppBar",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
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
                        IconButton(onClick = {
                            selectedColor = (selectedColor + 1) % colors.size
                        }) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(MaterialTheme.shapes.small)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(MaterialTheme.shapes.small)
                                            .background(colors[selectedColor])
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(MaterialTheme.shapes.small)
                                            .background(colors[(selectedColor + 1) % colors.size])
                                    )
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    currentColor,
                                    currentColor.copy(alpha = 0.8f)
                                )
                            )
                        )
                )

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = currentColor.copy(alpha = 0.1f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        colors.forEachIndexed { index, color ->
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        color = color,
                                        shape = MaterialTheme.shapes.small
                                    )
                            )
                        }
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("This example shows a Colorful AppBar with:")
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Dynamic color changes")
            Text("• Gradient background")

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorfulAppBarExamplePreview() {
    ColorfulAppBarExample()
}
