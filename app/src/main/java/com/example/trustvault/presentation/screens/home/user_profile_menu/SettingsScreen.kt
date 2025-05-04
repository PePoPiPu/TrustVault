package com.example.trustvault.presentation.screens.home.user_profile_menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trustvault.presentation.utils.GradientTrackSwitch
import com.example.trustvault.presentation.viewmodels.home.user_profile_menu.SettingsScreenViewModel
import com.example.trustvault.presentation.viewmodels.onboarding.ThemeSelectionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel = hiltViewModel(),
    themeViewModel: ThemeSelectionViewModel = hiltViewModel()
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(true) }

    var darkTheme = viewModel.darkTheme

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Ajustes") },
                navigationIcon = {
                    IconButton(onClick = { /* Back action */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF121212),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                modifier = Modifier.padding(top = 25.dp)
            )
        },
        containerColor = Color(0xFF121212)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            SettingSwitchItem(
                icon = Icons.Default.Notifications,
                title = "Notificaciones",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )

            SettingSwitchItem(
                icon = Icons.Default.Brightness6,
                title = "Modo Oscuro/Claro",
                checked = darkModeEnabled,
                onCheckedChange = { darkModeEnabled = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingItem(Icons.Default.Star, "Calificar App")
            SettingItem(Icons.Default.Share, "Compartir App")
            SettingItem(Icons.Default.Lock, "Política de privacidad")
            SettingItem(Icons.Default.Description, "Términos y Condiciones")
            SettingItem(Icons.Default.Email, "Contacto")

            Spacer(modifier = Modifier.weight(1f))

            // Logout
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Log out */ }
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar Sesión", color = Color.White)
            }
        }
    }
}

@Composable
fun SettingSwitchItem(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = Color.White)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = Color.White, modifier = Modifier.weight(1f))
        GradientTrackSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SettingItem(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Navigate */ }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = Color.White)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = Color.White)
    }
}
