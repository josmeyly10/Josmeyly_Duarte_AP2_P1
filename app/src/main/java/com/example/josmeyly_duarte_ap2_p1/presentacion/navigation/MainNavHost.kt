package com.example.josmeyly_duarte_ap2_p1.presentacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.josmeyly_duarte_ap2_p1.presentacion.edit.EditCervezaScreen
import com.example.josmeyly_duarte_ap2_p1.presentacion.list.ListCervezaScreen

@Composable
fun CervezaNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ListCerveza.route
    ) {
        composable(route = Screen.ListCerveza.route) {
            ListCervezaScreen(
                onNavigateToEdit = { cervezaId ->
                    navController.navigate(Screen.EditCerveza.createRoute(cervezaId))
                }
            )
        }

        composable(
            route = Screen.EditCerveza.route,
            arguments = listOf(
                navArgument("cervezaId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            EditCervezaScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}