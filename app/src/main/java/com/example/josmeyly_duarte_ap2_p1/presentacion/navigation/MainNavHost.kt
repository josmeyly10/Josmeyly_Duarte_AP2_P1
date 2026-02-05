package com.example.josmeyly_duarte_ap2_p1.presentacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable
fun MainNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.BorrameList
    ) {

        composable<Screen.BorrameList> {

        }


        composable<Screen.BorrameEdit> {


        }
    }
}