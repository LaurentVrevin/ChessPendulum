package com.laurentvrevin.chesspendulum.presentation.navigation

sealed class Screen(val route: String) {
    data object Config : Screen("config")
    data object Game : Screen("game")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { append("/$it") }
        }
    }
}