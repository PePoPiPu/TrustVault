package com.example.trustvault.presentation.screens.onboarding

import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trustvault.R
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.DarkModePrimaryGradient
import com.example.trustvault.presentation.theme.DisabledButtonGradient
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.theme.LightModePrimaryGradient
import com.example.trustvault.presentation.viewmodels.onboarding.ForgotPasswordViewModel

@Composable
fun ForgotPassword(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    onGoBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    val darkTheme = viewModel.darkTheme
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier // Create this column and the attributes
            .fillMaxSize()
            //.fillMaxHeight()// Make the layout fill all available space
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background),
        verticalArrangement = Arrangement.Top, // Vertical Alignment
        horizontalAlignment = Alignment.CenterHorizontally // Horizontal Alignment
    ) {
        Spacer(modifier = Modifier.height(65.dp))
        // Go back icon
        Row (
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 32.dp, start = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = if (darkTheme) painterResource(id = R.drawable.ic_go_back) else painterResource(id = R.drawable.ic_go_back_black),
                contentDescription = "Go Back Button",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        onGoBackClick()
                    }
            )
        }
        Spacer(modifier = Modifier.height(195.dp))
        // Title
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Recuperación de contraseña",
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(Color(0xFFEB41EE), Color(0xFF6F82FF), Color(0xFFFFBB77))
                    ),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.height(50.dp))

        // Form Fields
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            // Username Input
            OutlinedTextField(
                value = viewModel.email,
                singleLine = true,
                onValueChange = { viewModel.email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(0.9f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF2F2F2),
                    unfocusedContainerColor = Color(0xFFF2F2F2),
                    disabledContainerColor = Color(0xFFF2F2F2),
                    focusedLabelColor = if (darkTheme) Color.White else Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        LaunchedEffect(viewModel.senPassResult.value) {
            val result = viewModel.senPassResult.value

            if(result != null) {
                if(result == true) {
                    onContinueClick()
                    Toast.makeText(context, "Email sent!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Email doesn't exist", Toast.LENGTH_LONG).show()
                }
            }
        }
        Button (
            onClick = {
                viewModel.sendPasswordEmail()
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Transparent as it is the same color of the background
            contentPadding = PaddingValues(),
            enabled = viewModel.isFormValid
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        when {
                            darkTheme && viewModel.isFormValid -> DarkModePrimaryGradient
                            !darkTheme && viewModel.isFormValid -> LightModePrimaryGradient
                            else -> DisabledButtonGradient  // Greyed out when form is not filled out
                        },
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("Continuar", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}