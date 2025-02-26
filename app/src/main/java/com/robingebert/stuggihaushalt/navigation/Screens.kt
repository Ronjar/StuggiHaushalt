package com.robingebert.stuggihaushalt.navigation

sealed class Screen(val route: String, val title: String) {
    data object Settings: Screen("settings", "Settings")
    data object Main : Screen("main", "Vorschläge")
    data object Login : Screen("login", "Login")
}
