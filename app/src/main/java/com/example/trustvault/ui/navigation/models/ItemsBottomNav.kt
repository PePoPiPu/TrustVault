package com.example.trustvault.ui.navigation.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PermScanWifi
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.trustvault.ui.navigation.NavScreen

/**
 * A sealed class representing the bottom navigation items in the application.
 *
 * This class is used to define a restricted set of navigation items for the bottom navigation bar.
 * Each item consists of an icon, title, and a route string that corresponds to the screen in the app.
 * Sealed classes are useful here because they restrict subclassing, ensuring that only the defined
 * bottom navigation items can exist, providing better control over the navigation logic.
 *
 * @property icon The icon associated with the navigation item, represented as an [ImageVector].
 * @property title The title text to be displayed for the navigation item.
 * @property route The route string that corresponds to the screen in the app.
 *
 * @author Alex Álvarez de Sotomayor Sugimoto
 */
sealed class ItemsBottomNav( // restricted class where values can have several of the defined types but no other types outside of it
    val icon: ImageVector,
    val title: String,
    var route: String
) {
    object ItemBottomNav1: ItemsBottomNav( // object creates a singleton object
        Icons.Outlined.Search,
        "Buscar",
        NavScreen.SearchScreen.name
    )
    object ItemBottomNav2: ItemsBottomNav(
        Icons.Outlined.Home,
        "Inicio",
        NavScreen.HomeScreen.name
    )
    object ItemBottomNav3: ItemsBottomNav(
        Icons.Outlined.PermScanWifi,
        "Monitoreo Dark Web",
        NavScreen.DarkWebMonitorScreen.name
    )
    object ItemBottomNav4: ItemsBottomNav(
        Icons.Outlined.PersonOutline,
        "Usuario",
        NavScreen.UserProfileScreen.name
    )
}