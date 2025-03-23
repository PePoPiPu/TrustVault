package com.example.trustvault.ui.navigation

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomAppBar
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.trustvault.domain.models.ItemsBottomNav

@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    val menuItems = listOf(
        ItemsBottomNav.ItemBottomNav1,
        ItemsBottomNav.ItemBottomNav2,
        ItemsBottomNav.ItemBottomNav3,
        ItemsBottomNav.ItemBottomNav4
    )
    BottomAppBar {
        NavigationBar {
            menuItems.forEach { item ->
                val selected = currentRoute(navController) == item.route
                NavigationBarItem(
                    selected = selected,
                    onClick = { navController.navigate(item.route) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
    }
}