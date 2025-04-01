package com.laurentvrevin.chesspendulum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GameSettings(
    val initialTimeMillis: Long,
    val incrementMillis: Long,
    val player1Name: String = "White",
    val player2Name: String = "Black"
)