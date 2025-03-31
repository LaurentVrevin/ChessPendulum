package com.laurentvrevin.chesspendulum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    initialTime: Long,
    private val incrementMillis: Long
) : ViewModel() {

    private val initialTotalTime = initialTime + incrementMillis

    private val _activePlayer = MutableStateFlow(1)
    val activePlayer: StateFlow<Int> = _activePlayer

    private var timerJob: Job? = null
    private var lastTickTime = 0L
    private var timerRunning = false
    private var hasStarted = false

    private val _player1Time = MutableStateFlow(initialTotalTime)
    val player1Time: StateFlow<Long> = _player1Time

    private val _player2Time = MutableStateFlow(initialTotalTime)
    val player2Time: StateFlow<Long> = _player2Time

    private val _gameOver = MutableStateFlow(false)
    val gameOver: StateFlow<Boolean> = _gameOver

    private val _winner = MutableStateFlow(0)
    val winner: StateFlow<Int> = _winner

    fun onPlayerClick(player: Int) {
        if (_gameOver.value) return

        if (!hasStarted) {
            hasStarted = true
            _activePlayer.value = player
            lastTickTime = System.currentTimeMillis()
            startTimer()
            return
        }

        updateTime()
        applyIncrement()
        _activePlayer.value = 3 - _activePlayer.value
        lastTickTime = System.currentTimeMillis()
    }

    private fun startTimer() {
        timerRunning = true
        lastTickTime = System.currentTimeMillis()
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (timerRunning) {
                delay(100L)
                updateTime()
            }
        }
    }

    private fun updateTime() {
        if (lastTickTime == 0L) return
        val now = System.currentTimeMillis()
        val elapsed = now - lastTickTime
        lastTickTime = now

        if (_activePlayer.value == 1) {
            _player1Time.value = (_player1Time.value - elapsed).coerceAtLeast(0L)
            if (_player1Time.value <= 0L) endGame(winner = 2)
        } else {
            _player2Time.value = (_player2Time.value - elapsed).coerceAtLeast(0L)
            if (_player2Time.value <= 0L) endGame(winner = 1)
        }
    }

    private fun applyIncrement() {
        if (_activePlayer.value == 1) {
            _player1Time.value += incrementMillis
        } else {
            _player2Time.value += incrementMillis
        }
    }

    private fun endGame(winner: Int) {
        timerRunning = false
        timerJob?.cancel()
        _gameOver.value = true
        _winner.value = winner
    }

    fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}