package com.dilara.composeexample.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SegmentedButtonGroupExample() {
    var selected by remember { mutableStateOf(0) }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        (1..3).forEach { i ->
            val shape = when (i) {
                1 -> RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp)
                3 -> RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
                else -> RoundedCornerShape(0.dp)
            }
            Button(
                onClick = { selected = i },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected == i) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = shape,
                modifier = Modifier.padding(0.dp)
            ) { Text("Segment $i") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSegmentedButtonGroupExample() {
    SegmentedButtonGroupExample()
} 