package com.dilara.composeexample.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShapeColorButtonExample() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(4.dp)
        ) { Text("Oval") }
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
            shape = RoundedCornerShape(0),
            modifier = Modifier.padding(4.dp)
        ) { Text("Köşeli") }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShapeColorButtonExample() {
    ShapeColorButtonExample()
} 