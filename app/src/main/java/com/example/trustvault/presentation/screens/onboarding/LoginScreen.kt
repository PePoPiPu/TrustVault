package com.example.trustvault.presentation.screens.onboarding

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ComponentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.trustvault.R
import com.example.trustvault.domain.models.User
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.DarkModePrimaryGradient
import com.example.trustvault.presentation.theme.DisabledButtonGradient
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.theme.LightModePrimaryGradient
import com.example.trustvault.presentation.viewmodels.home.HomeScreenViewModel
import com.example.trustvault.presentation.viewmodels.onboarding.LoginScreenViewModel

    /**
     * Function that represents the Login Screen
     * It is designed with different styles based on the dark/light theme of the app.
     *
     * This screen allows users to log in by entering their username and password. It provides
     * navigation to go back, and a link to the registration screen if the user does not have an account.
     *
     * @param darkTheme A boolean flag indicating whether the app is in dark theme mode. This affects
     * the colors and styles of the UI components. If true, dark theme is applied; otherwise, the light theme is used.
     * @param viewModel The [LoginScreenViewModel] that holds the state and business logic of the Login screen.
     * It manages the username, password, and form validity states.
     * @param onGoBackClick A lambda function that will be called when the "Go Back" button is clicked.
     * This can be used to navigate to the previous screen.
     * @param onRegisterClick A lambda function that will be called when the "Register" link is clicked.
     * This can be used to navigate to the registration screen.
     *
     * @author David Pires Manzanares
     */

    @SuppressLint("RestrictedApi")
    @Composable // Composable object
    fun LoginScreen(
        viewModel: LoginScreenViewModel = hiltViewModel(),
        onGoBackClick: () -> Unit = {},
        onRegisterClick: () -> Unit = {},
        onContinueClick: () -> Unit = {}
    ) {

        val darkTheme = viewModel.darkTheme
        val isRegistered = viewModel.registrationStatus
        val context = LocalContext.current

       if(isRegistered) {
           BiometricLoginScreen()
       }

        Column (
            modifier = Modifier // Create this column and the attributes
                .fillMaxSize()
                .fillMaxHeight()// Make the layout fill all available space
                .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background),
            verticalArrangement = Arrangement.Center, // Vertical Alignment
            horizontalAlignment = Alignment.CenterHorizontally // Horizontal Alignment
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

            // Title
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Bienvenido de nuevo",
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            listOf(Color(0xFFEB41EE), Color(0xFF6F82FF), Color(0xFFFFBB77))
                        ),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 200.dp, bottom = 32.dp)
                )
            }
            // Form Fields
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                // Username Input
                OutlinedTextField(
                    value = viewModel.username,
                    singleLine = true,
                    onValueChange = { viewModel.username = it },
                    label = { Text("Usuario") },
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

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                // Password Input
                OutlinedTextField(
                    value = viewModel.password,
                    singleLine = true,
                    onValueChange = { viewModel.password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
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

            // Check if the login was successful before going to the next screen
            // Avoids issues with asynchronous operations firing after
            // and not grabbing the userId in the StoredAccountRepositoryImpl
            LaunchedEffect(viewModel.loginResult.value) {
                val result = viewModel.loginResult.value

                if(result != null) {
                    if(result == true) {
                        onContinueClick()
                    } else {
                        Toast.makeText(context, "El usuario o la contraseña son incorrectos", Toast.LENGTH_LONG).show()
                    }
                }
            }

            Button (
                onClick = {
                    viewModel.loginUser()
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

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    style = TextStyle(
                        color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold

                    ),
                    modifier = Modifier
                        .padding(top = 250.dp, bottom = 16.dp)
                        .clickable {
                            onRegisterClick()
                        }
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¿Has olvidado la contraseña?",
                    style = TextStyle(
                        color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .clickable {
                            /* TODO: Go to Lost password activity */
                            Toast.makeText(context, "Te pasa por cafre, cacho mamón.", Toast.LENGTH_SHORT).show()
                        }
                )
            }
        }
    }

    @Composable
    fun ValidateLogin(loginResult: Result<User>, context: Context, onLoginSuccess: (Boolean) -> Unit) {
        LaunchedEffect(loginResult) {
            val result = loginResult
            if(result.isSuccess ) {
                Toast.makeText(context, "Inicio de sesión realizado con éxito", Toast.LENGTH_SHORT).show()
                onLoginSuccess(true)
            } else {
                Toast.makeText(context, "El usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT).show()
                onLoginSuccess(false)
            }
        }
    }