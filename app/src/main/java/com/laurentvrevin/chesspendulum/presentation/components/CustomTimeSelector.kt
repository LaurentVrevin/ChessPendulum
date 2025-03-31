package com.laurentvrevin.chesspendulum.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomTimeSelector(
    label: String,
    value: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(text = "$label: ", modifier = Modifier.width(120.dp))
        Button(onClick = onDecrement) { Text("-") }
        Spacer(modifier = Modifier.width(8.dp))
        Text("$value")
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = onIncrement) { Text("+") }
    }
}