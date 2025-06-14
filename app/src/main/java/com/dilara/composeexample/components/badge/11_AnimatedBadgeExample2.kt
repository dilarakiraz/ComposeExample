package com.dilara.composeexample.components.badge

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun AnimatedBadgeExample2() {
    val infiniteTransition = rememberInfiniteTransition(label = "animated")

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
        val morphProgress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000),
                repeatMode = RepeatMode.Reverse
            ),
            label = "morph"
        )

        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            MorphingBadge(
                progress = morphProgress,
                color = primaryColor,
                onColor = onPrimaryColor,
                modifier = Modifier.fillMaxSize()
            )
        }

        val particleProgress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000),
                repeatMode = RepeatMode.Restart
            ),
            label = "particle"
        )

        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            ParticleEffectBadge(
                progress = particleProgress,
                color = errorColor,
                onColor = onErrorColor,
                modifier = Modifier.fillMaxSize()
            )
        }

        val waveProgress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000),
                repeatMode = RepeatMode.Restart
            ),
            label = "wave"
        )

        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            WaveAnimationBadge(
                progress = waveProgress,
                color = tertiaryColor,
                onColor = onTertiaryColor,
                modifier = Modifier.fillMaxSize()
            )
        }

        val rotation3D by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing)
            ),
            label = "rotation3D"
        )

        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Badge3DTransformation(
                rotation = rotation3D,
                color = primaryColor,
                onColor = onPrimaryColor,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun MorphingBadge(
    progress: Float,
    color: Color,
    onColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val center = Offset(width / 2, height / 2)
        val radius = min(width, height) * 0.4f

        val points = 5
        val angleStep = 2 * PI / points
        val morphFactor = progress

        val path = Path().apply {
            for (i in 0 until points) {
                val angle = i * angleStep
                val outerRadius = radius
                val innerRadius = radius * 0.4f

                val outerX = center.x + cos(angle).toFloat() * outerRadius
                val outerY = center.y + sin(angle).toFloat() * outerRadius
                val innerX = center.x + cos(angle + angleStep / 2).toFloat() * innerRadius
                val innerY = center.y + sin(angle + angleStep / 2).toFloat() * innerRadius

                if (i == 0) {
                    moveTo(outerX, outerY)
                } else {
                    lineTo(outerX, outerY)
                }

                lineTo(innerX, innerY)
            }
            close()
        }

        drawPath(
            path = path,
            color = color,
            style = Fill
        )

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "Morph",
                center.x - 20,
                center.y + 5,
                android.graphics.Paint().apply {
                    setColor(onColor.toArgb())
                    textSize = 12f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }
    }
}

@Composable
private fun ParticleEffectBadge(
    progress: Float,
    color: Color,
    onColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val center = Offset(width / 2, height / 2)
        val radius = min(width, height) * 0.4f

        val particleCount = 12
        for (i in 0 until particleCount) {
            val angle = (i * 2 * PI / particleCount + progress * 2 * PI).toFloat()
            val distance = radius * (0.5f + progress * 0.5f)
            val x = center.x + cos(angle) * distance
            val y = center.y + sin(angle) * distance

            drawCircle(
                color = color,
                radius = 4f,
                center = Offset(x, y)
            )
        }

        drawCircle(
            color = color,
            radius = radius * 0.3f,
            center = center
        )

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "Particle",
                center.x - 25,
                center.y + 5,
                android.graphics.Paint().apply {
                    setColor(onColor.toArgb())
                    textSize = 12f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }
    }
}

@Composable
private fun WaveAnimationBadge(
    progress: Float,
    color: Color,
    onColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val center = Offset(width / 2, height / 2)
        val radius = min(width, height) * 0.4f

        val waveCount = 3
        for (i in 0 until waveCount) {
            val waveProgress = (progress + i.toFloat() / waveCount) % 1f
            val waveRadius = radius * (0.5f + waveProgress * 0.5f)
            val alpha = 1f - waveProgress

            drawCircle(
                color = color.copy(alpha = alpha),
                radius = waveRadius,
                center = center,
                style = Stroke(width = 2f)
            )
        }

        drawCircle(
            color = color,
            radius = radius * 0.3f,
            center = center
        )

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "Wave",
                center.x - 15,
                center.y + 5,
                android.graphics.Paint().apply {
                    setColor(onColor.toArgb())
                    textSize = 12f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }
    }
}

@Composable
private fun Badge3DTransformation(
    rotation: Float,
    color: Color,
    onColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val center = Offset(width / 2, height / 2)
        val radius = min(width, height) * 0.4f

        rotate(rotation) {
            scale(0.8f) {
                drawRect(
                    color = color,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2)
                )

                val sideColor = color.copy(alpha = 0.7f)
                drawRect(
                    color = sideColor,
                    topLeft = Offset(center.x, center.y - radius),
                    size = Size(radius, radius * 2)
                )
                drawRect(
                    color = sideColor,
                    topLeft = Offset(center.x - radius, center.y),
                    size = Size(radius * 2, radius)
                )
            }
        }

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "3D",
                center.x - 10,
                center.y + 5,
                android.graphics.Paint().apply {
                    setColor(onColor.toArgb())
                    textSize = 12f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedBadgeExamplePreview2() {
    MaterialTheme {
        AnimatedBadgeExample2()
    }
} 