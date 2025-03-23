package com.example.trustvault.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.trustvault.ui.navigation.AppNavHost
import com.example.trustvault.ui.navigation.BottomNavBar
import com.example.trustvault.ui.navigation.NavScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold (
        bottomBar = {
            BottomNavBar(navController)
        }
    ) {
       padding -> Box( // Scaffold padding goes into Box padding
           modifier = Modifier
               .padding(padding)
               .fillMaxSize()
       ) {
           AppNavHost(
               navController = navController,
               startDestination = NavScreen.HomeScreen.name)
    }
    }
}