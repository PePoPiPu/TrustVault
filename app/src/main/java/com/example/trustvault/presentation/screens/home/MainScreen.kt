package com.example.trustvault.presentation.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.trustvault.presentation.navigation.AppNavHost
import com.example.trustvault.presentation.navigation.BottomNavBar
import com.example.trustvault.presentation.navigation.NavScreen
import com.example.trustvault.presentation.viewmodels.home.HomeScreenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    Scaffold (
        bottomBar = {
            BottomNavBar(viewModel, navController)
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