package com.example.trustvault.presentation.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.trustvault.presentation.navigation.models.ItemsBottomNav
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.DarkModePrimaryGradient
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.theme.LightModePrimaryGradient
import com.example.trustvault.presentation.theme.TransparentGradient
import com.example.trustvault.presentation.viewmodels.home.HomeScreenViewModel

/**
 * A composable function that displays a custom bottom navigation bar.
 * The navigation bar includes animated indicators for each item.
 * The indicators animate their size when a navigation item is selected or unselected.
 *
 * @param navController The [NavHostController] that manages the navigation between destinations.
 *                      It is used to navigate to the selected route when a navigation item is clicked.
 *
 * @author Alex Álvarez de Sotomayor Sugimoto
 */
@Composable
fun BottomNavBar(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val darkTheme = viewModel.darkTheme
    val menuItems = listOf(
        ItemsBottomNav.ItemBottomNav1,
        ItemsBottomNav.ItemBottomNav2,
        ItemsBottomNav.ItemBottomNav3,
        ItemsBottomNav.ItemBottomNav4
    )

    NavigationBar (
        containerColor = if (darkTheme) DarkColorScheme.background else Color(0xFF8C9BFF),
        modifier = Modifier
            .fillMaxWidth()
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background)
            .clip(RoundedCornerShape(35.dp, 35.dp, 0.dp, 0.dp))
    ) {
        menuItems.forEach { item ->
            val selected = currentRoute(navController) == item.route

            val indicatorHeight by animateDpAsState(
                targetValue = if (selected) 35.dp else 50.dp,
                animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium)
            )

            val indicatorWidth by animateDpAsState(
                targetValue = if (selected) 55.dp else 70.dp,
                animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium)
            )

            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(item.route) },
                icon = {
                    Box( // Custom indicator to allow the use of gradients
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(indicatorHeight)
                            .width(indicatorWidth)
                            .background(
                                if (darkTheme) {
                                    if (selected) DarkModePrimaryGradient else TransparentGradient
                                } else {
                                    if (selected) LightModePrimaryGradient else TransparentGradient
                                },
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = if (selected) DarkColorScheme.surface else if (darkTheme) DarkColorScheme.onSurface else LightColorScheme.surface
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // Hide default indicator
                )
            )
        }
    }
}
