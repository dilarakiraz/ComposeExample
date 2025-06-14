package com.dilara.composeexample.components.badge

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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dilara.composeexample.R

@Composable
fun NotificationBadgeExample() {
    var unreadCount by remember { mutableStateOf(0) }
    val infiniteTransition = rememberInfiniteTransition(label = "notification")

    val primaryColor = MaterialTheme.colorScheme.primary
    val errorColor = MaterialTheme.colorScheme.error
    val surfaceVariantColor = MaterialTheme.colorScheme.surfaceVariant
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    val onErrorColor = MaterialTheme.colorScheme.onError

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
            BasicNotificationBadge(
                count = unreadCount,
                primaryColor = primaryColor,
                onPrimaryColor = onPrimaryColor
            )

            BasicNotificationBadge(
                count = 99,
                primaryColor = errorColor,
                onPrimaryColor = onErrorColor
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PriorityNotificationBadge(
                priority = "High",
                color = errorColor,
                onColor = onErrorColor
            )

            PriorityNotificationBadge(
                priority = "Medium",
                color = primaryColor,
                onColor = onPrimaryColor
            )

            PriorityNotificationBadge(
                priority = "Low",
                color = surfaceVariantColor,
                onColor = MaterialTheme.colorScheme.onSurfaceVariant
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
                    text = "Group Notifications",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    GroupNotificationBadge(
                        type = "Messages",
                        count = unreadCount,
                        color = primaryColor,
                        onColor = onPrimaryColor
                    )
                    GroupNotificationBadge(
                        type = "Emails",
                        count = 5,
                        color = errorColor,
                        onColor = onErrorColor
                    )
                    GroupNotificationBadge(
                        type = "Alerts",
                        count = 2,
                        color = MaterialTheme.colorScheme.tertiary,
                        onColor = MaterialTheme.colorScheme.onTertiary
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
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_mail),
                contentDescription = "Mail",
                modifier = Modifier.size(24.dp)
            )
            Badge(
                modifier = Modifier
                    .padding(4.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    },
                containerColor = errorColor
            ) {
                Text("!")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { if (unreadCount > 0) unreadCount-- }
            ) {
                Text("Decrease")
            }

            Text(
                text = "Count: $unreadCount",
                style = MaterialTheme.typography.titleMedium
            )

            Button(
                onClick = { unreadCount++ }
            ) {
                Text("Increase")
            }
        }
    }
}

@Composable
private fun BasicNotificationBadge(
    count: Int,
    primaryColor: Color,
    onPrimaryColor: Color
) {
    Box(
        modifier = Modifier.size(48.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_mail),
            contentDescription = "Mail",
            modifier = Modifier.size(24.dp)
        )
        Badge(
            modifier = Modifier.padding(4.dp),
            containerColor = primaryColor
        ) {
            Text(
                text = if (count > 99) "99+" else count.toString(),
                color = onPrimaryColor
            )
        }
    }
}

@Composable
private fun PriorityNotificationBadge(
    priority: String,
    color: Color,
    onColor: Color
) {
    Box(
        modifier = Modifier.size(48.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_mail),
            contentDescription = "Mail",
            modifier = Modifier.size(24.dp)
        )
        Badge(
            modifier = Modifier.padding(4.dp),
            containerColor = color
        ) {
            Text(
                text = priority,
                color = onColor,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun GroupNotificationBadge(
    type: String,
    count: Int,
    color: Color,
    onColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = type,
            style = MaterialTheme.typography.bodySmall
        )
        Badge(
            containerColor = color
        ) {
            Text(
                text = count.toString(),
                color = onColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationBadgeExamplePreview() {
    MaterialTheme {
        NotificationBadgeExample()
    }
} 