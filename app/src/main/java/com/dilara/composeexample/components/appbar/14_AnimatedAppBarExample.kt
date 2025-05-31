package com.dilara.composeexample.components.appbar

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedAppBarExample(onBackClick: () -> Unit = {}) {
    var isExpanded by remember { mutableStateOf(false) }
    val colors = listOf(
        Color(0xFF6200EE),
        Color(0xFF03DAC6),
        Color(0xFF018786),
        Color(0xFFBB86FC)
    )

    val currentColorIndex = remember { mutableStateOf(0) }
    val transition = updateTransition(currentColorIndex.value, label = "color")
    val backgroundColor by transition.animateColor(
        label = "backgroundColor",
        transitionSpec = {
            tween(1000)
        }
    ) { colors[it] }

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            while (true) {
                currentColorIndex.value = (currentColorIndex.value + 1) % colors.size
                delay(2000)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isExpanded) "Animated AppBar" else "Static AppBar",
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
                    IconButton(onClick = { isExpanded = !isExpanded }) {
                        Icon(
                            if (isExpanded) Icons.Default.Close else Icons.Default.PlayArrow,
                            contentDescription = if (isExpanded) "Stop" else "Play",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
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
            Text("This example shows an animated AppBar with:")
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Color transitions")
            Text("• Play/Stop animation")
            Text("• Smooth color changes")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Animation is ${if (isExpanded) "running" else "stopped"}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedAppBarExamplePreview() {
    AnimatedAppBarExample()
}
