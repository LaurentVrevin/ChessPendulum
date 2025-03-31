package com.laurentvrevin.chesspendulum.domain.usecase

class TimeManager(
private val incrementMillis: Long
) {
    fun applyIncrement(currentTime: Long): Long = currentTime + incrementMillis

    fun subtractElapsed(currentTime: Long, elapsed: Long): Long =
        (currentTime - elapsed).coerceAtLeast(0L)
}