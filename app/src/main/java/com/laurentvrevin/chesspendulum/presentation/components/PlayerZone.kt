package com.laurentvrevin.chesspendulum.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.laurentvrevin.chesspendulum.R

@Composable
fun PlayerZone(
    name: String,
    time: String,
    isActive: Boolean,
    onClick: () -> Unit,
    darkTheme: Boolean = false,
    moveCount: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(if (isActive) (if (darkTheme) Color.Black else Color.White) else Color.Gray)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(name, color = if (darkTheme) Color.White else Color.Black)
            Text(
                text = time,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = if (darkTheme) Color.White else Color.Black
            )
            Text(
                text = stringResource(id = R.string.moves_played, moveCount),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if (darkTheme) Color.White else Color.Black
            )
        }
    }
}