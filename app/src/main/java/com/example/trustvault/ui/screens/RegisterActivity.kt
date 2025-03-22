package com.example.trustvault.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trustvault.R
import com.example.trustvault.ui.theme.DarkColorScheme
import com.example.trustvault.ui.theme.DarkModePrimaryGradient
import com.example.trustvault.ui.theme.DisabledButtonGradient
import com.example.trustvault.ui.theme.LightColorScheme
import com.example.trustvault.ui.theme.LightModePrimaryGradient
import com.example.trustvault.ui.viewmodels.RegisterViewModel

class RegisterActivity {
    @Composable
    fun RegisterScreen(
        darkTheme: Boolean,
        viewModel: RegisterViewModel = viewModel(),
        onGoBackClick: () -> Unit = {}
    ) {

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            // Go back icon
            Row (
                modifier = Modifier
                    .fillMaxWidth(0.9f),
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

            Spacer(Modifier.height(40.dp))

            // Title
            Text(
                text = "Queremos conocerte mejor",
                fontSize = 26.sp,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = (listOf(Color(0xFFEB41EE), Color(0xFF6F82FF), Color(0xFFFFBB77)))
                    )
                )
            )
            Spacer(Modifier.height(40.dp))
            // Form Title
            Row (
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Información Personal",
                    color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start
                )
            }

            // Form Fields
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Email Input
                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    label = { Text("E-Mail") },
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF2F2F2),
                        unfocusedContainerColor = Color(0xFFF2F2F2),
                        disabledContainerColor = Color(0xFFF2F2F2)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                // Username Input
                OutlinedTextField(
                    value = viewModel.username,
                    onValueChange = { viewModel.username = it },
                    label = { Text("Nombre de usuario") },
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF2F2F2),
                        unfocusedContainerColor = Color(0xFFF2F2F2),
                        disabledContainerColor = Color(0xFFF2F2F2)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                // Phone Input
                OutlinedTextField(
                    value = viewModel.phone,
                    onValueChange = { viewModel.phone = it },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF2F2F2),
                        unfocusedContainerColor = Color(0xFFF2F2F2),
                        disabledContainerColor = Color(0xFFF2F2F2)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                // Password Input
                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = { viewModel.password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF2F2F2),
                        unfocusedContainerColor = Color(0xFFF2F2F2),
                        disabledContainerColor = Color(0xFFF2F2F2)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                // Confirm Password Input
                OutlinedTextField(
                    value = viewModel.confirmPassword,
                    onValueChange = { viewModel.confirmPassword = it },
                    label = { Text("Confirma tu contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF2F2F2),
                        unfocusedContainerColor = Color(0xFFF2F2F2),
                        disabledContainerColor = Color(0xFFF2F2F2),
                        focusedLabelColor = if (darkTheme) Color.White else Color.Black,
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Continue Button
            Button (
                onClick = { /* TODO: Handle registration logic and go to SMS Auth Activity */},
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
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}