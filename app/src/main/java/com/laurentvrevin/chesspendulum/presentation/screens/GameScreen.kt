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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.laurentvrevin.chesspendulum.R
import com.laurentvrevin.chesspendulum.presentation.components.PlayerZone
import com.laurentvrevin.chesspendulum.presentation.viewmodel.GameViewModel

@Composable
fun GameScreen(navController: NavController) {
    val haptics = LocalHapticFeedback.current
    val viewModel: GameViewModel = hiltViewModel()

    val p1Time by viewModel.player1Time.collectAsState()
    val p2Time by viewModel.player2Time.collectAsState()
    val p1Moves by viewModel.player1Moves.collectAsState()
    val p2Moves by viewModel.player2Moves.collectAsState()
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
                moveCount = p1Moves,
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
                moveCount = p2Moves,
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
