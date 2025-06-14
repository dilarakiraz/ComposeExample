package com.dilara.composeexample.components.badge

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Circular Progress Badge
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressBadge(
                progress = progress,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.titleMedium
            )
        }

        // Linear Progress Badge
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            LinearProgressBadge(
                progress = progress,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Step Progress Badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(5) { step ->
                StepProgressBadge(
                    isCompleted = step < (progress * 5).toInt(),
                    isCurrent = step == (progress * 5).toInt(),
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        // Animated Progress Badge
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
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.titleSmall
            )
        }

        // Progress Control
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
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = size.width * 0.1f
        val radius = (size.width - strokeWidth) / 2
        val center = Offset(size.width / 2, size.height / 2)
        
        // Background circle
        drawCircle(
            color = MaterialTheme.colorScheme.surfaceVariant,
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth)
        )
        
        // Progress arc
        drawArc(
            color = MaterialTheme.colorScheme.primary,
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
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
private fun StepProgressBadge(
    isCompleted: Boolean,
    isCurrent: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(
                when {
                    isCompleted -> MaterialTheme.colorScheme.primary
                    isCurrent -> MaterialTheme.colorScheme.primaryContainer
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isCompleted) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "Completed",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(
                text = if (isCurrent) "•" else "○",
                color = if (isCurrent) 
                    MaterialTheme.colorScheme.onPrimaryContainer 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
private fun AnimatedProgressBadge(
    rotation: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = size.width * 0.1f
        val radius = (size.width - strokeWidth) / 2
        val center = Offset(size.width / 2, size.height / 2)
        
        // Draw 8 dots in a circle
        repeat(8) { index ->
            val angle = (index * 45f + rotation) * (PI / 180f)
            val x = center.x + cos(angle).toFloat() * radius
            val y = center.y + sin(angle).toFloat() * radius
            
            drawCircle(
                color = MaterialTheme.colorScheme.primary,
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