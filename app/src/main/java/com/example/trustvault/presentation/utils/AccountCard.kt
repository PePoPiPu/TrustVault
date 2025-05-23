package com.example.trustvault.presentation.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trustvault.presentation.models.AccountItem
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trustvault.presentation.viewmodels.home.HomeScreenViewModel
import com.example.trustvault.R

@Composable
fun AccountCard(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    account: AccountItem,
    openDetailedAccountCard: () -> Unit = {}
    ) {
    val darkTheme =  viewModel.darkTheme
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Keep it square
            .clickable { openDetailedAccountCard() },
        colors = CardDefaults.cardColors(
            if (darkTheme) Color(0xFF1E1E1E) else Color(0xFF5F74FF)
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = if(account.iconResId != null) account.iconResId else R.drawable.question_mark),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(account.name, color = Color.White, fontWeight = FontWeight.Bold)
            Text(account.email, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp, textAlign = TextAlign.Center)
        }
    }
}