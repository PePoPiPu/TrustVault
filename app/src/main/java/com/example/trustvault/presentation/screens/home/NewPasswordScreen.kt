package com.example.trustvault.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.DarkModePrimaryGradient
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.theme.LightModePrimaryGradient
import com.example.trustvault.presentation.utils.rememberImeState
import com.example.trustvault.presentation.viewmodels.home.PasswordViewModel

@Composable
fun NewPasswordScreen(
    viewModel: PasswordViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    val darkTheme = viewModel.darkTheme
    val focusManager = LocalFocusManager.current
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    var platform by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background)
            .padding(16.dp)
    ) {
        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Nueva contraseña",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Form
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1C1C2A), shape = RoundedCornerShape(24.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Añade tu nueva contraseña",
                    fontSize = 20.sp,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = (listOf(Color(0xFFEB41EE), Color(0xFF6F82FF), Color(0xFFFFBB77)))
                        )
                    )
                )

                // Input Fields
                OutlinedTextField(
                    value = viewModel.platformName,
                    onValueChange = { viewModel.platformName = it },
                    singleLine = true,
                    label = { Text("Plataforma") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions (onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF2F2F2),
                        unfocusedContainerColor = Color(0xFFF2F2F2),
                        disabledContainerColor = Color(0xFFF2F2F2),
                        errorContainerColor = Color(0xFFF2F2F2),
                        focusedLabelColor = if (darkTheme) Color.White else Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions (onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF2F2F2),
                        unfocusedContainerColor = Color(0xFFF2F2F2),
                        disabledContainerColor = Color(0xFFF2F2F2),
                        errorContainerColor = Color(0xFFF2F2F2),
                        focusedLabelColor = if (darkTheme) Color.White else Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = { viewModel.password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus() // hides the keyboard
                    }),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF2F2F2),
                        unfocusedContainerColor = Color(0xFFF2F2F2),
                        disabledContainerColor = Color(0xFFF2F2F2),
                        errorContainerColor = Color(0xFFF2F2F2),
                        focusedLabelColor = if (darkTheme) Color.White else Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Continue Button
                Button (
                    onClick = {
                        viewModel.addAccount()
                        onContinueClick()
                    },
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
                                if(darkTheme) DarkModePrimaryGradient else LightModePrimaryGradient,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Continuar", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
