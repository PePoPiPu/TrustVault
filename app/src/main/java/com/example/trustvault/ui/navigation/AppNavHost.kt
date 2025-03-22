package com.example.trustvault.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trustvault.ui.screens.GetStartedActivity
import com.example.trustvault.ui.screens.LoginScreen
import com.example.trustvault.ui.screens.RegisterActivity

/**
 * This sealed class represents different screens in the app using data objects.
 *
 * @property route The navigation route associated with the screen.
 *
 * @author Alex Álvarez de Sotomayor Sugimoto
 */
sealed class Screen(val route: String) {
    data object GetStarted : Screen("GetStartedActivity")
    data object Login : Screen("Login")
    data object Register : Screen("RegisterActivity")
}

/**
 * App navigation host that manages screen transitions and navigation between screens.
 *
 * @param modifier Modifier for styling the navigation host.
 * @param navController Controller for handling navigation between screens.
 * @param startDestination The first screen that is displayed when the app starts.
 *
 * @author Alex Álvarez de Sotomayor Sugimoto
 */
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
        composable(
            route = Screen.GetStarted.route,
            exitTransition = { // Only exit transition since this is the first screen
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700) // can be tween or spring
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700) // can be tween or spring
                )
            }
        ) {
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
        composable(
            route = Screen.Login.route,
            enterTransition = { // entering the login screen
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700) // can be tween or spring
                )
            },
            exitTransition = { // when you exit the login screen
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700) // can be tween or spring
                )
            },
        ) {
            LoginScreen(
                darkTheme = true,
                onGoBackClick = {
                    navController.navigate(Screen.GetStarted.route)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(
            route = Screen.Register.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700) // can be tween or spring
                )
            },
            exitTransition = { // when you exit the login screen
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700) // can be tween or spring
                )
            },
        ) {
            RegisterActivity().RegisterScreen(
                darkTheme = true,
                onGoBackClick = {
                    navController.navigate(Screen.GetStarted.route)
                }
            )
        }
    }
}