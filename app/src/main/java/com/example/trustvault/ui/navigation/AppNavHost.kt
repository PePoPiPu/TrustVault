package com.example.trustvault.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trustvault.ui.screens.home.DarkWebMonitor
import com.example.trustvault.ui.screens.home.HomeScreen
import com.example.trustvault.ui.screens.home.MainScreen
import com.example.trustvault.ui.screens.home.SearchScreen
import com.example.trustvault.ui.screens.home.UserProfile
import com.example.trustvault.ui.screens.onboarding.GetStartedScreen
import com.example.trustvault.ui.screens.onboarding.LoaderScreen
import com.example.trustvault.ui.screens.onboarding.LoginScreen
import com.example.trustvault.ui.screens.onboarding.RegisterActivity
import com.example.trustvault.ui.screens.onboarding.SMSAuthScreen
import com.example.trustvault.ui.screens.onboarding.ThemeSelectionScreen

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

    data object MainScreen : Screen("MainScreen")
    data object LoaderScreen : Screen("LoadingScreenActivity")
    data object ThemeSelection : Screen("ThemeSelectionActivity")
    data object SMSAuth : Screen("SMSAuthScreen")

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
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.GetStarted.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
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
            GetStartedScreen(
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                onRegisterClick = {
                    navController.navigate(Screen.ThemeSelection.route)
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
                onGoBackClick = {
                    navController.navigate(Screen.GetStarted.route)
                },
                onRegisterClick = {
                    navController.navigate(Screen.ThemeSelection.route)
                }
            )
        }

        composable(
            route = Screen.ThemeSelection.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700)
                )
            }
        ) {
            ThemeSelectionScreen(
                onContinueClick = {
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
                onGoBackClick = {
                    navController.navigate(Screen.ThemeSelection.route)
                },
                onContinueClick = {
                    navController.navigate(Screen.SMSAuth.route)
                }
            )
        }

        composable (
            route = Screen.SMSAuth.route,
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
            SMSAuthScreen(
                onContinueClick = {
                    navController.navigate(Screen.LoaderScreen.route)
                }
            )
        }

        composable (
            route = Screen.LoaderScreen.route,
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
            LoaderScreen(
                goToMainScreenAfterWait = {
                    navController.navigate(Screen.MainScreen.route)
                }
            )
        }

        composable(
            route = Screen.MainScreen.route
        ) {
            MainScreen()
        }
        composable(
            route = NavScreen.HomeScreen.name
        ) {
            HomeScreen()
        }
        composable(
            route = NavScreen.SearchScreen.name
        ) {
            SearchScreen()
        }
        composable(
            route = NavScreen.DarkWebMonitorScreen.name
        ) {
            DarkWebMonitor()
        }
        composable(
            route = NavScreen.UserProfileScreen.name
        ) {
            UserProfile()
        }
    }
}