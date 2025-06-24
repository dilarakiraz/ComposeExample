package com.dilara.composeexample.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun DraggableButtonExample() {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                .draggable(
                    state = rememberDraggableState { delta ->
                        offsetX += delta
                    },
                    orientation = androidx.compose.foundation.gestures.Orientation.Horizontal
                )
                .draggable(
                    state = rememberDraggableState { delta ->
                        offsetY += delta
                    },
                    orientation = androidx.compose.foundation.gestures.Orientation.Vertical
                ),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {}, modifier = Modifier) {
                Text("Sürükle")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDraggableButtonExample() {
    DraggableButtonExample()
} 