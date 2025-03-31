package com.laurentvrevin.chesspendulum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laurentvrevin.chesspendulum.domain.usecase.FormatTimeUseCase
import com.laurentvrevin.chesspendulum.domain.usecase.TimeManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    initialTime: Long,
    incrementMillis: Long,
    private val formatTimeUseCase: FormatTimeUseCase = FormatTimeUseCase(),
    private val timeManager: TimeManager = TimeManager(incrementMillis)
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
            _player1Time.value = timeManager.subtractElapsed(_player1Time.value, elapsed)
            if (_player1Time.value <= 0L) endGame(winner = 2)
        } else {
            _player2Time.value = timeManager.subtractElapsed(_player2Time.value, elapsed)
            if (_player2Time.value <= 0L) endGame(winner = 1)
        }
    }

    private fun applyIncrement() {
        if (_activePlayer.value == 1) {
            _player1Time.value = timeManager.applyIncrement(_player1Time.value)
        } else {
            _player2Time.value = timeManager.applyIncrement(_player2Time.value)
        }
    }

    private fun endGame(winner: Int) {
        timerRunning = false
        timerJob?.cancel()
        _gameOver.value = true
        _winner.value = winner
    }

    fun formatTime(millis: Long): String = formatTimeUseCase(millis)
}