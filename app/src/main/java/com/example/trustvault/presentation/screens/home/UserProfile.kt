package com.example.trustvault.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.viewmodels.home.UserProfileViewModel
import com.example.trustvault.R

@Composable
fun UserProfile(
    viewModel: UserProfileViewModel = hiltViewModel(),
    onFavoritesClick: () -> Unit = {},
    onTrashClick: () -> Unit = {},
    onSupportClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onDarkWebClick: () -> Unit = {}
) {

    val darkTheme = viewModel.darkTheme
    val context = LocalContext.current

    val navItems = listOf("Favoritos", "Papelera", "Soporte", "Ajustes", "Monitoreo Dark Web")
    val icons = listOf(
        Icons.Default.Favorite,
        Icons.Default.Delete,
        Icons.Default.SupportAgent,
        Icons.Default.Settings,
        Icons.Default.Visibility
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background) // Dark background
            .padding(16.dp)
    ) {
        // Profile Section
        Card(
            colors = CardDefaults.cardColors(
                if (darkTheme) Color(0xFF1E1E1E) else Color(0xFF5F74FF)
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Aniceto González García", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("anicetogonzalez@gmail.com", color = if(darkTheme) Color.Gray else Color.White)
                    Button(
                        onClick = { TODO("Edit profile") },
                        colors = ButtonDefaults.buttonColors(containerColor = if(darkTheme) Color(0xFFBB86FC) else Color(0xFF99FFE2)),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Editar Perfil", color = if(darkTheme) Color.White else Color.Black)
                    }
                }
                Image(
                    painter = painterResource(R.drawable.default_avatar), // Replace with your image
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Menu Items
        navItems.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        when(index) {
                            0 -> onFavoritesClick()
                            1 -> onTrashClick()
                            2 -> onSupportClick()
                            3 -> onSettingsClick()
                            4 -> onDarkWebClick()
                        }
                    }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icons[index], contentDescription = item, tint = if(darkTheme) Color.White else Color.Black)
                Spacer(modifier = Modifier.width(16.dp))
                Text(item, color = if(darkTheme) Color.White else Color.Black)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Logout
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.logout(context) }
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión", tint = if(darkTheme) Color.White else Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cerrar Sesión", color = if(darkTheme) Color.White else Color.Black)
        }
    }
}