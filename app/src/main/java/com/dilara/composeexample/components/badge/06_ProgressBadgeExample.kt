package com.dilara.composeexample.components.badge

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dilara.composeexample.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ProgressBadgeExample() {
    var progress by remember { mutableStateOf(0f) }
    val infiniteTransition = rememberInfiniteTransition(label = "progress")

    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceVariantColor = MaterialTheme.colorScheme.surfaceVariant
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant
    val primaryContainerColor = MaterialTheme.colorScheme.primaryContainer
    val onPrimaryContainerColor = MaterialTheme.colorScheme.onPrimaryContainer

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressBadge(
                progress = progress,
                primaryColor = primaryColor,
                surfaceVariantColor = surfaceVariantColor,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(surfaceVariantColor)
        ) {
            LinearProgressBadge(
                progress = progress,
                primaryColor = primaryColor,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleMedium,
                color = onSurfaceVariantColor
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(5) { step ->
                StepProgressBadge(
                    isCompleted = step < (progress * 5).toInt(),
                    isCurrent = step == (progress * 5).toInt(),
                    primaryColor = primaryColor,
                    primaryContainerColor = primaryContainerColor,
                    surfaceVariantColor = surfaceVariantColor,
                    onPrimaryColor = onPrimaryColor,
                    onPrimaryContainerColor = onPrimaryContainerColor,
                    onSurfaceVariantColor = onSurfaceVariantColor,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing)
            ),
            label = "rotation"
        )

        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedProgressBadge(
                rotation = rotation,
                primaryColor = primaryColor,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.titleSmall
            )
        }

        Slider(
            value = progress,
            onValueChange = { progress = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CircularProgressBadge(
    progress: Float,
    primaryColor: Color,
    surfaceVariantColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = size.width * 0.1f
        val radius = (size.width - strokeWidth) / 2
        val center = Offset(size.width / 2, size.height / 2)

        drawCircle(
            color = surfaceVariantColor,
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        drawArc(
            color = primaryColor,
            startAngle = -90f,
            sweepAngle = progress * 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )
    }
}

@Composable
private fun LinearProgressBadge(
    progress: Float,
    primaryColor: Color,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .clip(RoundedCornerShape(20.dp))
                .background(primaryColor)
        )
    }
}

@Composable
private fun StepProgressBadge(
    isCompleted: Boolean,
    isCurrent: Boolean,
    primaryColor: Color,
    primaryContainerColor: Color,
    surfaceVariantColor: Color,
    onPrimaryColor: Color,
    onPrimaryContainerColor: Color,
    onSurfaceVariantColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(
                when {
                    isCompleted -> primaryColor
                    isCurrent -> primaryContainerColor
                    else -> surfaceVariantColor
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isCompleted) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "Completed",
                tint = onPrimaryColor
            )
        } else {
            Text(
                text = if (isCurrent) "•" else "○",
                color = if (isCurrent) onPrimaryContainerColor else onSurfaceVariantColor,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
private fun AnimatedProgressBadge(
    rotation: Float,
    primaryColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = size.width * 0.1f
        val radius = (size.width - strokeWidth) / 2
        val center = Offset(size.width / 2, size.height / 2)

        repeat(8) { index ->
            val angle = (index * 45f + rotation) * (PI / 180f)
            val x = center.x + cos(angle).toFloat() * radius
            val y = center.y + sin(angle).toFloat() * radius

            drawCircle(
                color = primaryColor,
                radius = strokeWidth / 2,
                center = Offset(x, y),
                alpha = 0.3f + (index * 0.1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressBadgeExamplePreview() {
    MaterialTheme {
        ProgressBadgeExample()
    }
} 