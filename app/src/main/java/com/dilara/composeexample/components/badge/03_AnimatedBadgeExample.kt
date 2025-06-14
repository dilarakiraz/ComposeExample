package com.dilara.composeexample.components.badge

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import com.dilara.composeexample.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedBadgeExample() {
    var notificationCount by remember { mutableStateOf(0) }
    val infiniteTransition = rememberInfiniteTransition(label = "badge")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulse"
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
                    }
            ) {
                Text("!")
            }
        }

        AnimatedContent(
            targetState = notificationCount,
            transitionSpec = {
                slideInVertically { height -> height } + fadeIn() with
                        slideOutVertically { height -> -height } + fadeOut()
            },
            label = "counter"
        ) { count ->
            BadgedBox(
                badge = {
                    if (count > 0) {
                        Badge {
                            Text(count.toString())
                        }
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mail),
                    contentDescription = "Mail"
                )
            }
        }

        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "Profile",
                modifier = Modifier.size(24.dp)
            )
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.error)
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "New",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        Button(
            onClick = { notificationCount = (notificationCount + 1) % 10 }
        ) {
            Text("Add Notification")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedBadgeExamplePreview() {
    MaterialTheme {
        AnimatedBadgeExample()
    }
} 