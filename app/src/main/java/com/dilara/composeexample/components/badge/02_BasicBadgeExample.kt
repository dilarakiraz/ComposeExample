package com.dilara.composeexample.components.badge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dilara.composeexample.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicBadgeExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BadgedBox(
            badge = {
                Badge {
                    Text("5")
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
                    Text("New")
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_mail),
                contentDescription = "Mail"
            )
        }

        // Colored badge
        BadgedBox(
            badge = {
                Badge(
                    containerColor = MaterialTheme.colorScheme.error
                ) {
                    Text("99+")
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_mail),
                contentDescription = "Mail"
            )
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
            Badge(
                modifier = Modifier.padding(4.dp)
            ) {
                Text("1")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BasicBadgeExamplePreview() {
    MaterialTheme {
        BasicBadgeExample()
    }
} 