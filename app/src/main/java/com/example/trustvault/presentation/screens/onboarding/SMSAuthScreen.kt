package com.example.trustvault.presentation.screens.onboarding

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ComponentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.trustvault.R
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.DarkModePrimaryGradient
import com.example.trustvault.presentation.theme.DisabledButtonGradient
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.theme.LightModePrimaryGradient
import com.example.trustvault.presentation.viewmodels.SMSAuthScreenViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("RestrictedApi")
@Composable
    // TODO: As soon as this screen is painted, send a code to the users phone number and check it automatically, if possible
    fun SMSAuthScreen(
        onContinueClick: () -> Unit = {}
    ) {
        // Shared ViewModel to allow persistance of data between register and auth screen
        val activity = LocalContext.current as ComponentActivity
        val viewModel: SMSAuthScreenViewModel = hiltViewModel(activity as ViewModelStoreOwner)

        val darkTheme = viewModel.darkTheme
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            // Go back icon
            Row (
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_go_back),
                    contentDescription = "Go Back Button",
                    modifier = Modifier.size(25.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Title
            Text(
                text = "Te hemos enviado un código",
                fontSize = 26.sp,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = (listOf(Color(0xFFFFBB77), Color(0xFF6F82FF), Color(0xFFEB41EE)))
                    )
                )
            )

            // Message
            Text(
                text = "El código ha sido enviado al ${viewModel.parsedPhoneNumber}", // TODO: Bring phone number from the previous form/saved number from the user
                fontSize = 18.sp,
                color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Resend code button
            Text(
                text = "Reenviar el código en 00:30", // TODO: Start a 30 sec countdown, replace with a resend button once it finishes
                fontSize = 18.sp,
                color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Variables to handle keyboard focus
            val focusManager = LocalFocusManager.current
            val focusRequesters = List(6) { FocusRequester() }

            Row (
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) { repeat(6) { index ->
                // Repeat allows us to paint x times the elements that are inside the statement. Avoids code repetition
                // Gradient brush to call it from the Canvas
                val gradientBrush = if (darkTheme) {
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF5D1D6C), Color(0xFF5B3C93), Color(0xFF5A69CB), Color(0xFF921BAF))
                    )
                } else
                {
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF5D1D6C), Color(0xFF5B3C93), Color(0xFF5A69CB), Color(0xFF921BAF))
                    )
                }

                Box(
                    modifier = Modifier
                        .size(46.dp)
                ) {
                    // We have to draw the background first. Jetpack compose doesn't allow gradients in Box borders directly
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White, shape = RoundedCornerShape(6.dp))
                            .padding(16.dp)
                    )
                    // Then we draw a Canvas with the box border with the gradient
                    // We draw the box first as the border is painted on top of it
                    Canvas(modifier = Modifier.matchParentSize()) {
                        drawRoundRect(
                            brush = gradientBrush, //Gradient border
                            style = Stroke(width = 8f), // Border thickness
                            cornerRadius = CornerRadius(16f, 16f)
                        )
                    }

                    // Text field for the code digits
                    BasicTextField(
                        value = viewModel.code[index],
                        onValueChange = {
                            if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                viewModel.code[index] = it
                            }
                        },
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = if (index == 5) ImeAction.Done else ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                if (index < 5) {
                                    focusRequesters[index + 1].requestFocus()
                                } else {
                                    focusManager.clearFocus()
                                }
                            },
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .focusRequester(focusRequesters[index])
                    )
                }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            // Continue Button
            Button (
                onClick = {
                    coroutineScope.launch {
                        viewModel.verifyCode(context)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Transparent as it is the same color of the background
                contentPadding = PaddingValues(),
                enabled = viewModel.isFormValid()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            when {
                                darkTheme && viewModel.isFormValid() -> DarkModePrimaryGradient
                                !darkTheme && viewModel.isFormValid() -> LightModePrimaryGradient
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

        LaunchedEffect (viewModel.verificationResult.value) { // launched whenever verificationResult.value changes
            val verificationResult = viewModel.verificationResult.value
            if(verificationResult != null) {
                Log.d("VERRESULT", verificationResult.toString())
                if (verificationResult == true) {
                    onContinueClick()
                } else {
                    Toast.makeText(context, "¡Código incorrecto!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }