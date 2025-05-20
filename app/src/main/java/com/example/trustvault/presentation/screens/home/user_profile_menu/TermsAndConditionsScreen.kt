package com.example.trustvault.presentation.screens.home.user_profile_menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trustvault.R
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.viewmodels.home.HomeScreenViewModel
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val darkTheme = viewModel.darkTheme
    val context = LocalContext.current
    var termsAndConditions by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        termsAndConditions = loadMarkdown(context, R.raw.terms_and_conditions)
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Política de privacidad") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = if (darkTheme) Color.White else Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (darkTheme) DarkColorScheme.surface else LightColorScheme.background,
                    titleContentColor = if (darkTheme) Color.White else Color.Black,
                    navigationIconContentColor = if (darkTheme) Color.White else Color.Black
                ),
                modifier = Modifier.padding(top = 25.dp)
            )
        },
        containerColor = if (darkTheme) DarkColorScheme.surface else LightColorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Markdown(
                content = termsAndConditions,
                modifier = Modifier.fillMaxWidth(),
                colors = markdownColor(
                    text = if (darkTheme) Color.White else Color.Black
                ),
                typography = markdownTypography(
                    h1 = TextStyle(fontSize = 18.sp, color = if (darkTheme) Color.White else Color.Black, fontWeight = FontWeight.Bold),
                    h2 = TextStyle(fontSize = 16.sp, color = if (darkTheme) Color.White else Color.Black, fontWeight = FontWeight.Bold),
                    text = TextStyle(fontSize = 12.sp, color = if (darkTheme) Color.White else Color.Black),
                    list = TextStyle(fontSize = 12.sp, color = if (darkTheme) Color.White else Color.Black)
                )
            )
        }
    }
}