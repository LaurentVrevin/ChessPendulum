package com.laurentvrevin.chesspendulum.domain.usecase

class FormatTimeUseCase {
    operator fun invoke(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}