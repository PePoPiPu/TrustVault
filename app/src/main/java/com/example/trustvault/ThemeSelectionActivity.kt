package com.example.trustvault

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

class ThemeSelectionActivity {

    @Composable
    fun ThemeSelectionScreen(){
        var isDarkMode = remember { mutableStateOf(true) }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF111111)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Choose a theme title
            Text(
                text = "Elige un modo",
                fontSize = 40.sp,
                color = Color.White
            )
            // General text
            Text(
                text = "Presentamos el modo oscuro: \nuna interfaz elegante y amigable para la vista.",
                fontSize = 20.sp,
                color = Color(0xFFE9E9E9),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.img_dark_theme),
                contentDescription = "Dark Mode Illustration",
                modifier = Modifier.size(350.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = if (isDarkMode.value) "Modo Oscuro" else "Modo Claro",
                color = if (isDarkMode.value) Color.White else Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Calling DarkModeToggle composable and toggle isDarkMode mutable state when clicked
            DarkModeToggle(isDarkMode = isDarkMode.value) {
                isDarkMode.value = !isDarkMode.value
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Continue Button
            Button (
                onClick = { /* TODO: Go to SMS authentication activity */},
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Transparent as it is the same color of the background
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFBC39DB), Color(0xFF7C8EFF), Color(0xFFE7A3F8))
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Continuar", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }

    @SuppressLint("UseOfNonLambdaOffsetOverload")
    @Composable
    fun DarkModeToggle(isDarkMode: Boolean, onToggle: () -> Unit) {
        val offset = animateFloatAsState(
            targetValue = if (isDarkMode) 46f else 0f, label = ""
        )

        Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .clip(CircleShape)
                .background(Color.DarkGray)
                .clickable { onToggle() } // Calls function when clicked
                .padding(horizontal = 4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sun),
                    contentDescription = "Light Mode",
                    tint = Color.White,
                    modifier = Modifier
                        .size(26.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_moon),
                    contentDescription = "Dark Mode",
                    tint = Color.White,
                    modifier = Modifier
                        .size(26.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .zIndex(-1F)
                    .offset(x = offset.value.dp)  // Animated offset for the toggle
                    .clip(CircleShape)
                    .background(Color(0xFF2C2F38))
            )
        }
    }

    @Preview
    @Composable
    fun PreviewThemeSelectionScreen()
    {
        ThemeSelectionScreen()
    }
}