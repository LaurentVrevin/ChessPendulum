package com.laurentvrevin.chesspendulum.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.laurentvrevin.chesspendulum.R
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
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(if (activePlayer == 1) Color.Black else Color(0xFFCCCCCC))
                .clickable {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.onPlayerClick(1)
                },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(id = R.string.black), color = Color.White)
                    Text(
                        text = viewModel.formatTime(p1Time),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.rotate(180f),
                        color = Color.White
                    )
                }
            }
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(if (activePlayer == 2) Color.White else Color.LightGray)
                .clickable {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.onPlayerClick(2)
                },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(id = R.string.white), color = Color.Black)
                    Text(
                        text = viewModel.formatTime(p2Time),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Black
                    )
                }
            }
        }
    }
}