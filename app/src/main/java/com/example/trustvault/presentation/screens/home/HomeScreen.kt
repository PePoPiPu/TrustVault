package com.example.trustvault.presentation.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ComponentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.utils.AccountCard
import com.example.trustvault.presentation.utils.PasswordHealthCard
import com.example.trustvault.presentation.viewmodels.home.HomeScreenViewModel

@SuppressLint("RestrictedApi")
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onAddClick: () -> Unit = {}
) {

    // Shared viewModel between LoginScreen and HomeScreen
    val activity = LocalContext.current as ComponentActivity
    val viewModel: HomeScreenViewModel = hiltViewModel(activity as ViewModelStoreOwner)

    val darkTheme = viewModel.darkTheme
    var getAccountsResult = viewModel.getAccountsResult.value

    // We use launchedEffect(Unit) because it will only run the first time the composable
    // is composed
    LaunchedEffect(Unit) {
        viewModel.getAccounts()
        getAccountsResult = viewModel.getAccountsResult.value
    }



    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PasswordHealthCard(1f, "Hace 2 horas")

        LazyVerticalGrid (
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            if (getAccountsResult != null) {
                val accountItems = viewModel.getAccountItems(getAccountsResult!!)
                items(accountItems) { account ->
                    AccountCard(account)
                }
            }
        }
    }

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


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}