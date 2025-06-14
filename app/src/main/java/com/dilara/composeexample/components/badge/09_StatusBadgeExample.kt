package com.dilara.composeexample.components.badge

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dilara.composeexample.R

@Composable
fun StatusBadgeExample() {
    val infiniteTransition = rememberInfiniteTransition(label = "status")

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
            SystemStatusBadge(
                status = "Success",
                color = tertiaryColor,
                onColor = onTertiaryColor,
                icon = R.drawable.ic_check
            )

            SystemStatusBadge(
                status = "Error",
                color = errorColor,
                onColor = onErrorColor,
                icon = R.drawable.ic_error
            )

            SystemStatusBadge(
                status = "Warning",
                color = MaterialTheme.colorScheme.tertiaryContainer,
                onColor = MaterialTheme.colorScheme.onTertiaryContainer,
                icon = R.drawable.ic_warning
            )

            SystemStatusBadge(
                status = "Info",
                color = primaryColor,
                onColor = onPrimaryColor,
                icon = R.drawable.ic_info
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = LinearEasing)
                ),
                label = "rotation"
            )

            ProcessStatusBadge(
                status = "Loading",
                color = primaryColor,
                onColor = onPrimaryColor,
                icon = R.drawable.ic_refresh,
                rotation = rotation
            )

            ProcessStatusBadge(
                status = "Completed",
                color = tertiaryColor,
                onColor = onTertiaryColor,
                icon = R.drawable.ic_check
            )

            ProcessStatusBadge(
                status = "Pending",
                color = MaterialTheme.colorScheme.secondary,
                onColor = MaterialTheme.colorScheme.onSecondary,
                icon = R.drawable.ic_time
            )

            ProcessStatusBadge(
                status = "Cancelled",
                color = errorColor,
                onColor = onErrorColor,
                icon = R.drawable.ic_cancel
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
                    text = "Custom Status",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CustomStatusBadge(
                        text = "VIP",
                        color = MaterialTheme.colorScheme.tertiary,
                        onColor = onTertiaryColor
                    )
                    CustomStatusBadge(
                        text = "NEW",
                        color = primaryColor,
                        onColor = onPrimaryColor
                    )
                    CustomStatusBadge(
                        text = "HOT",
                        color = errorColor,
                        onColor = onErrorColor
                    )
                }
            }
        }

        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale"
        )

        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Badge(
                modifier = Modifier
                    .scale(scale),
                containerColor = errorColor
            ) {
                Text(
                    text = "!",
                    color = onErrorColor,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun SystemStatusBadge(
    status: String,
    color: Color,
    onColor: Color,
    icon: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Badge(
            containerColor = color
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = status,
                modifier = Modifier.size(16.dp),
                tint = onColor
            )
        }
        Text(
            text = status,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun ProcessStatusBadge(
    status: String,
    color: Color,
    onColor: Color,
    icon: Int,
    rotation: Float = 0f
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Badge(
            containerColor = color
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = status,
                modifier = Modifier
                    .size(16.dp)
                    .rotate(rotation),
                tint = onColor
            )
        }
        Text(
            text = status,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun CustomStatusBadge(
    text: String,
    color: Color,
    onColor: Color
) {
    Badge(
        containerColor = color
    ) {
        Text(
            text = text,
            color = onColor,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatusBadgeExamplePreview() {
    MaterialTheme {
        StatusBadgeExample()
    }
} 