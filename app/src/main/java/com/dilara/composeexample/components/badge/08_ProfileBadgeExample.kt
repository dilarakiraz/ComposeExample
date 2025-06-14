package com.dilara.composeexample.components.badge

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@Composable
fun ProfileBadgeExample() {
    val infiniteTransition = rememberInfiniteTransition(label = "profile")

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
            OnlineStatusBadge(
                status = "Online",
                color = tertiaryColor,
                onColor = onTertiaryColor,
                backgroundColor = surfaceVariantColor
            )

            OnlineStatusBadge(
                status = "Away",
                color = errorColor,
                onColor = onErrorColor,
                backgroundColor = surfaceVariantColor
            )

            OnlineStatusBadge(
                status = "Busy",
                color = errorColor,
                onColor = onErrorColor,
                backgroundColor = surfaceVariantColor
            )
        }

        val progress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000),
                repeatMode = RepeatMode.Reverse
            ),
            label = "progress"
        )

        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            ProfileCompletionBadge(
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

        // Achievement badges
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AchievementBadge(
                title = "New User",
                color = primaryColor,
                onColor = onPrimaryColor
            )

            AchievementBadge(
                title = "Verified",
                color = tertiaryColor,
                onColor = onTertiaryColor
            )

            AchievementBadge(
                title = "Premium",
                color = errorColor,
                onColor = onErrorColor
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(surfaceVariantColor)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "User Roles",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    RoleBadge(
                        role = "Admin",
                        color = errorColor,
                        onColor = onErrorColor
                    )
                    RoleBadge(
                        role = "Moderator",
                        color = primaryColor,
                        onColor = onPrimaryColor
                    )
                    RoleBadge(
                        role = "Member",
                        color = tertiaryColor,
                        onColor = onTertiaryColor
                    )
                }
            }
        }
    }
}

@Composable
private fun OnlineStatusBadge(
    status: String,
    color: Color,
    onColor: Color,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(backgroundColor)
                .border(2.dp, color, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "Profile",
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = status,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun ProfileCompletionBadge(
    progress: Float,
    primaryColor: Color,
    surfaceVariantColor: Color,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
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
}

@Composable
private fun AchievementBadge(
    title: String,
    color: Color,
    onColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "Achievement",
                modifier = Modifier.size(24.dp),
                tint = onColor
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun RoleBadge(
    role: String,
    color: Color,
    onColor: Color
) {
    Badge(
        containerColor = color
    ) {
        Text(
            text = role,
            color = onColor,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileBadgeExamplePreview() {
    MaterialTheme {
        ProfileBadgeExample()
    }
} 