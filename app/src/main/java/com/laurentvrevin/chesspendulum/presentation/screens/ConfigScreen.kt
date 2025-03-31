package com.laurentvrevin.chesspendulum.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.laurentvrevin.chesspendulum.R
import com.laurentvrevin.chesspendulum.data.model.GameMode

@Composable
fun ConfigScreen(navController: NavController) {
    var selectedMode by remember { mutableStateOf(GameMode.BLITZ) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Select your game mode", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        GameMode.values().forEach { mode ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = selectedMode == mode,
                    onClick = { selectedMode = mode }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = mode.label)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            val timeMillis = selectedMode.timeMinutes * 60 * 1000L
            val incrementMillis = selectedMode.incrementSeconds * 1000L
            navController.navigate("game/${timeMillis}_${incrementMillis}")
        }) {
            Text("Start Game")
        }
    }
}