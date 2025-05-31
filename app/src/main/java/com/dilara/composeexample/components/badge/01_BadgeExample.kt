package com.dilara.composeexample.components.badge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dilara.composeexample.R

@Composable
fun BadgeExample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Badge Examples",
            style = MaterialTheme.typography.headlineMedium
        )

        BadgedBox(
            badge = {
                Badge {
                    Text("5")
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = "Notifications"
            )
        }

        BadgedBox(
            badge = {
                Badge {
                    Text("99+")
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_mail),
                contentDescription = "Mail"
            )
        }

        BadgedBox(
            badge = {
                Badge {
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_shopping_cart),
                contentDescription = "Cart"
            )
        }

        BadgedBox(
            badge = {
                Badge {
                    Text("New")
                }
            }
        ) {
            Text(
                "Messages",
                style = MaterialTheme.typography.titleMedium
            )
        }

        BadgedBox(
            badge = {
                Badge {
                    Text("3")
                }
            }
        ) {
            Button(onClick = { /* TODO */ }) {
                Text("Inbox")
            }
        }

        BadgedBox(
            badge = {
                Badge(
                    containerColor = MaterialTheme.colorScheme.error
                ) {
                    Text("Error")
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_warning),
                contentDescription = "Warning"
            )
        }

        BadgedBox(
            badge = {
                Badge(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = Color.White
                        )
                        Text("Premium")
                    }
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BadgeExamplePreview() {
    MaterialTheme {
        BadgeExample()
    }
} 