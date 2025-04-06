package com.example.trustvault.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * A Composable function that retrieves the current route from the [NavHostController].
 *
 * This function extracts the route of the current back stack entry from the navigation controller
 * and returns it as a nullable [String]. The route corresponds to the current screen in the app.
 * This is particularly useful for determining which screen is currently being displayed is used
 * in the UI logic of the Bottom Navigation Bar in the Main Screen.
 *
 * @param navController The [NavHostController] instance responsible for navigation.
 *                      It holds the current navigation state and back stack.
 * @return The current route of the destination in the navigation back stack, or null if no route is found.
 *
 * @author Alex Álvarez de Sotomayor Sugimoto
 */
@Composable
fun currentRoute(navController: NavHostController): String? =
    navController.currentBackStackEntryAsState().value?.destination?.route