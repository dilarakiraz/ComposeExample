package com.dilara.composeexample.components.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationAppBarExample(
    onBackClick: () -> Unit = {}
) {
    var showNotifications by remember { mutableStateOf(false) }
    val notifications = remember {
        listOf(
            "New message from John",
            "Meeting reminder at 2 PM",
            "Task deadline approaching",
            "New comment on your post"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Notifications",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { showNotifications = !showNotifications }) {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = "Notifications"
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                                .align(Alignment.TopEnd)
                        ) {
                            Text(
                                text = "4",
                                color = Color.White,
                                fontSize = 10.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        DropdownMenu(
                            expanded = showNotifications,
                            onDismissRequest = { showNotifications = false }
                        ) {
                            notifications.forEach { notification ->
                                DropdownMenuItem(
                                    text = { Text(notification) },
                                    onClick = { showNotifications = false },
                                    leadingIcon = {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(Color.Gray)
                                        )
                                    }
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
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
            Text("This example shows a Notification AppBar with:")
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Notification counter badge")
            Text("• Dropdown notification list")
            Text("• Interactive notification menu")
            Text("• Real-time notification updates")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationAppBarExamplePreview() {
    NotificationAppBarExample()
}
