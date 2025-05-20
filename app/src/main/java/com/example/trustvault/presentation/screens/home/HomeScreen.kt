package com.example.trustvault.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trustvault.presentation.models.AccountItem
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.utils.AccountCard
import com.example.trustvault.presentation.utils.DetailedAccountCard
import com.example.trustvault.presentation.utils.PasswordHealthCard
import com.example.trustvault.presentation.viewmodels.home.HomeScreenViewModel

@SuppressLint("RestrictedApi")
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onAddClick: () -> Unit = {}
) {

    val darkTheme = viewModel.darkTheme
    val getAccountsResult by viewModel.getAccountsResult.observeAsState()
    var selectedAccount by remember { mutableStateOf<AccountItem?>(null) }

    // We use launchedEffect(Unit) because it will only run the first time the composable
    // is composed/recomposed
    LaunchedEffect(Unit) {
        viewModel.getAccounts()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PasswordHealthCard(viewModel, 1f, "Hace 2 horas")

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .let {
                        if (selectedAccount != null) it.blur(8.dp) else it
                    },
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                if (getAccountsResult != null) {
                    val accountItems = viewModel.getAccountItems(getAccountsResult!!)
                    items(accountItems) { account ->
                        AccountCard(viewModel, account, openDetailedAccountCard = { selectedAccount = account })
                    }
                }
            }
        }

        // Floating Add Button
        FloatingActionButton(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = if (darkTheme) DarkColorScheme.background else Color(0xFF5F74FF)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = if (darkTheme) Color.White else Color.Black,
                contentDescription = "Add"
            )
        }

        // Show Detailed View with overlay
        if (selectedAccount != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { selectedAccount = null }, // dismiss on background tap
                contentAlignment = Alignment.Center
            ) {
                DetailedAccountCard(
                    onBackClick = { selectedAccount = null },
                    accountDetails = selectedAccount!!
                )
            }
        }
    }
}