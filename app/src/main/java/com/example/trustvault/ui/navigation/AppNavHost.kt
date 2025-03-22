package com.example.trustvault.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trustvault.ui.screens.GetStartedActivity
import com.example.trustvault.ui.screens.LoginActivity
import com.example.trustvault.ui.screens.RegisterActivity

sealed class Screen(val route: String) {
    data object GetStarted : Screen("GetStartedActivity")
    data object Login : Screen("Login")
    data object Register : Screen("RegisterActivity")
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.GetStarted.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.GetStarted.route) {
            GetStartedActivity().GetStartedScreen(
                darkTheme = true,
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(Screen.Login.route) {
            LoginActivity().LoginScreen(
                darkTheme = true,
                onGoBackClick = {
                    navController.navigate(Screen.GetStarted.route)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterActivity().RegisterScreen(
                darkTheme = true,
                onGoBackClick = {
                    navController.navigate(Screen.GetStarted.route)
                }
            )
        }
    }
}