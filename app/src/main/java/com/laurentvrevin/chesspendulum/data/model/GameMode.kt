package com.laurentvrevin.chesspendulum.data.model

enum class GameMode(val label: String, val timeMinutes: Int, val incrementSeconds: Int) {
    BULLET("1+0", 1, 0),
    BULLET_INCREMENT("1+1", 1, 1),
    BLITZ("3+0", 3, 0),
    BLITZ_INCREMENT("3+2", 3, 2),
    RAPID("10+0", 10, 0),
    CUSTOM("Custom", 0, 0)
}