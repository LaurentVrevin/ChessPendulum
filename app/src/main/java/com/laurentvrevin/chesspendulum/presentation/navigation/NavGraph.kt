package com.laurentvrevin.chesspendulum.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.laurentvrevin.chesspendulum.presentation.screens.ConfigScreen
import com.laurentvrevin.chesspendulum.presentation.screens.GameScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "config") {
        composable("config") {
            ConfigScreen(navController)
        }
        composable(
            "game/{initialTime}_{incrementMillis}",
            arguments = listOf(
                navArgument("initialTime") { type = NavType.StringType },
                navArgument("incrementMillis") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            GameScreen(navController, backStackEntry)
        }
    }
}
