package com.example.trustvault.presentation.screens.home.user_profile_menu

import android.content.Context
import android.os.Build
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
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
import com.example.trustvault.R
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current
    var privacyPolicy by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        privacyPolicy = loadMarkdown(context, R.raw.privacy_policy)
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Política de privacidad") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
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
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Markdown(
                content = privacyPolicy,
                modifier = Modifier.fillMaxWidth(),
                colors = markdownColor(
                    text = Color.White
                ),
                typography = markdownTypography(
                    h1 = TextStyle(fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold),
                    h2 = TextStyle(fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.SemiBold),
                    h3 = TextStyle(fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.SemiBold),
                    text = TextStyle(fontSize = 12.sp, color = Color.White),
                    list = TextStyle(fontSize = 12.sp, color = Color.White)
                )
            )
        }
    }
}

fun loadMarkdown(context: Context, @RawRes rawResId: Int) :String {
    return context.resources.openRawResource(rawResId)
        .bufferedReader()
        .use { it.readText() }
}
