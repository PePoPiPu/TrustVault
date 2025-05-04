package com.example.trustvault.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.viewmodels.home.HomeScreenViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onAddClick: () -> Unit = {}
) {

    val darkTheme = viewModel.darkTheme
    var getAccountResult = viewModel.getAccountsResult.value

    // We use launchedEffect(Unit) because it will only run the first time the composable
    // is composed
    LaunchedEffect(Unit) {
        viewModel.getAccounts() 
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box( // add Button
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = if (darkTheme) DarkColorScheme.background else LightColorScheme.surface
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = if (darkTheme) Color.White else Color.Black,
                    contentDescription = "Add"
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}