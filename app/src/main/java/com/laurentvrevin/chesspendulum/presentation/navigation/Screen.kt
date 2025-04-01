package com.laurentvrevin.chesspendulum.presentation.navigation

sealed class Screen(val route: String) {
    data object Config : Screen("config")
    data object Game : Screen("game")

    fun withParams(initialTime: Long, incrementMillis: Long): String {
        return "$route?initialTime=$initialTime&incrementMillis=$incrementMillis"
    }
}