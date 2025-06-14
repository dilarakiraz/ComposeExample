package com.dilara.composeexample.components.badge

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun InteractiveBadgeExample() {
    val infiniteTransition = rememberInfiniteTransition(label = "interactive")

    val primaryColor = MaterialTheme.colorScheme.primary
    val errorColor = MaterialTheme.colorScheme.error
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val surfaceVariantColor = MaterialTheme.colorScheme.surfaceVariant
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    val onErrorColor = MaterialTheme.colorScheme.onError
    val onTertiaryColor = MaterialTheme.colorScheme.onTertiary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            var selectedBadge by remember { mutableStateOf("") }

            ClickableBadge(
                text = "Option 1",
                isSelected = selectedBadge == "Option 1",
                color = primaryColor,
                onColor = onPrimaryColor,
                onClick = { selectedBadge = "Option 1" },
                backgroundColor = surfaceVariantColor
            )

            ClickableBadge(
                text = "Option 2",
                isSelected = selectedBadge == "Option 2",
                color = primaryColor,
                onColor = onPrimaryColor,
                onClick = { selectedBadge = "Option 2" },
                backgroundColor = surfaceVariantColor
            )

            ClickableBadge(
                text = "Option 3",
                isSelected = selectedBadge == "Option 3",
                color = primaryColor,
                onColor = onPrimaryColor,
                onClick = { selectedBadge = "Option 3" },
                backgroundColor = surfaceVariantColor
            )
        }

        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(surfaceVariantColor)
                .padding(16.dp)
        ) {
            DraggableBadge(
                text = "Drag Me",
                color = tertiaryColor,
                onColor = onTertiaryColor,
                offsetX = offsetX,
                offsetY = offsetY,
                onDrag = { x, y ->
                    offsetX = x
                    offsetY = y
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            var expanded1 by remember { mutableStateOf(false) }
            var expanded2 by remember { mutableStateOf(false) }

            ExpandableBadge(
                text = "Details 1",
                expanded = expanded1,
                color = errorColor,
                onColor = onErrorColor,
                onToggle = { expanded1 = !expanded1 }
            )

            ExpandableBadge(
                text = "Details 2",
                expanded = expanded2,
                color = errorColor,
                onColor = onErrorColor,
                onToggle = { expanded2 = !expanded2 }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(surfaceVariantColor)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Custom Interactive",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val scale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 1.2f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "scale"
                    )

                    CustomInteractiveBadge(
                        text = "Scale",
                        color = primaryColor,
                        onColor = onPrimaryColor,
                        scale = scale
                    )

                    CustomInteractiveBadge(
                        text = "Rotate",
                        color = tertiaryColor,
                        onColor = onTertiaryColor,
                        rotation = infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 360f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(2000, easing = LinearEasing)
                            ),
                            label = "rotation"
                        ).value
                    )
                }
            }
        }
    }
}

@Composable
private fun ClickableBadge(
    text: String,
    isSelected: Boolean,
    color: Color,
    onColor: Color,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Badge(
        modifier = Modifier
            .scale(scale)
            .clip(RoundedCornerShape(16.dp)),
        containerColor = if (isSelected) color else backgroundColor
    ) {
        Text(
            text = text,
            color = if (isSelected) onColor else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun DraggableBadge(
    text: String,
    color: Color,
    onColor: Color,
    offsetX: Float,
    offsetY: Float,
    onDrag: (Float, Float) -> Unit
) {
    Badge(
        modifier = Modifier
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onDrag(offsetX + dragAmount.x, offsetY + dragAmount.y)
                }
            },
        containerColor = color
    ) {
        Text(
            text = text,
            color = onColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun ExpandableBadge(
    text: String,
    expanded: Boolean,
    color: Color,
    onColor: Color,
    onToggle: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Badge(
            containerColor = color,
            modifier = Modifier.clip(RoundedCornerShape(16.dp))
        ) {
            Text(
                text = text,
                color = onColor,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Text(
                text = "Additional information for $text",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun CustomInteractiveBadge(
    text: String,
    color: Color,
    onColor: Color,
    scale: Float = 1f,
    rotation: Float = 0f
) {
    Badge(
        modifier = Modifier
            .scale(scale)
            .rotate(rotation),
        containerColor = color
    ) {
        Text(
            text = text,
            color = onColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InteractiveBadgeExamplePreview() {
    MaterialTheme {
        InteractiveBadgeExample()
    }
} 