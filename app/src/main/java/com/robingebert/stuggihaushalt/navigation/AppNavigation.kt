package com.robingebert.stuggihaushalt.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.robingebert.stuggihaushalt.feature_login.LoginScreen
import com.robingebert.stuggihaushalt.feature_settings.SettingsScreen
import com.robingebert.stuggihaushalt.feature_swiping.SwipingScreen

@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(
        navController,
        startDestination = Screen.Login.route,
        enterTransition = { slideInHorizontally(initialOffsetX = { -2000 }, animationSpec = tween(700)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -2000 }, animationSpec = tween(700)) }
    ) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Main.route) { SwipingScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
    }
}