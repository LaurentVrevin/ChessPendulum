package com.laurentvrevin.chesspendulum.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.laurentvrevin.chesspendulum.R
import com.laurentvrevin.chesspendulum.data.model.GameMode
import com.laurentvrevin.chesspendulum.presentation.components.CustomTimeSelector
import com.laurentvrevin.chesspendulum.presentation.components.GameModeSelector
import com.laurentvrevin.chesspendulum.presentation.navigation.Screen

@Composable
fun ConfigScreen(navController: NavController) {
    var selectedMode by remember { mutableStateOf(GameMode.BLITZ) }
    var customMinutes by remember { mutableIntStateOf(5) }
    var customIncrement by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(stringResource(id = R.string.select_game_mode), style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        GameModeSelector(selectedMode) { selectedMode = it }

        if (selectedMode == GameMode.CUSTOM) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.custom_settings))

            CustomTimeSelector(
                label = stringResource(id = R.string.minutes),
                value = customMinutes,
                onDecrement = { if (customMinutes > 1) customMinutes-- },
                onIncrement = { if (customMinutes < 60) customMinutes++ }
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTimeSelector(
                label = stringResource(id = R.string.increment_seconds),
                value = customIncrement,
                onDecrement = { if (customIncrement > 0) customIncrement-- },
                onIncrement = { if (customIncrement < 60) customIncrement++ }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            val timeMinutes = if (selectedMode == GameMode.CUSTOM) customMinutes else selectedMode.timeMinutes
            val incrementSeconds = if (selectedMode == GameMode.CUSTOM) customIncrement else selectedMode.incrementSeconds
            val timeMillis = timeMinutes * 60 * 1000L
            val incrementMillis = incrementSeconds * 1000L
            navController.navigate(
                Screen.Game.withParams(
                    initialTime = timeMillis,
                    incrementMillis = incrementMillis
                )
            )
        }) {
            Text(stringResource(id = R.string.start_game))
        }
    }
}