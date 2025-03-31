package com.laurentvrevin.chesspendulum.domain.model

data class Player(
    val name: String,
    val remainingTime: Long,
    val isActive: Boolean = false
)