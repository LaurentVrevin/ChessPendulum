package com.laurentvrevin.chesspendulum.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.laurentvrevin.chesspendulum.R
import com.laurentvrevin.chesspendulum.presentation.components.PlayerZone
import com.laurentvrevin.chesspendulum.presentation.viewmodel.GameViewModel

@Composable
fun GameScreen(navController: NavController, backStackEntry: NavBackStackEntry) {
    val haptics = LocalHapticFeedback.current
    val args = backStackEntry.arguments
    val initialTime = args?.getString("initialTime")?.toLongOrNull() ?: 0L
    val incrementMillis = args?.getString("incrementMillis")?.toLongOrNull() ?: 0L

    val viewModel: GameViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return GameViewModel(initialTime, incrementMillis) as T
        }
    })

    val p1Time by viewModel.player1Time.collectAsState()
    val p2Time by viewModel.player2Time.collectAsState()
    val activePlayer by viewModel.activePlayer.collectAsState()
    val gameOver by viewModel.gameOver.collectAsState()
    val winner by viewModel.winner.collectAsState()

    if (gameOver) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.player_wins, winner),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Red
            )
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            PlayerZone(
                name = stringResource(id = R.string.black),
                time = viewModel.formatTime(p1Time),
                isActive = activePlayer == 1,
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.onPlayerClick(1)
                },
                darkTheme = true,
                modifier = Modifier.weight(1f)
            )
            PlayerZone(
                name = stringResource(id = R.string.white),
                time = viewModel.formatTime(p2Time),
                isActive = activePlayer == 2,
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.onPlayerClick(2)
                },
                darkTheme = false,
                modifier = Modifier.weight(1f)
            )
        }
    }
}