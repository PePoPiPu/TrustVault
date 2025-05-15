package com.example.trustvault.presentation.screens.onboarding

import android.annotation.SuppressLint
import android.hardware.biometrics.BiometricPrompt
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ComponentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.arpitkatiyarprojects.countrypicker.CountryPickerOutlinedTextField
import com.arpitkatiyarprojects.countrypicker.enums.CountryListDisplayType
import com.arpitkatiyarprojects.countrypicker.models.CountryDetails
import com.arpitkatiyarprojects.countrypicker.utils.CountryPickerUtils
import com.example.trustvault.R
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.DarkModePrimaryGradient
import com.example.trustvault.presentation.theme.DisabledButtonGradient
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.theme.LightModePrimaryGradient
import com.example.trustvault.presentation.utils.rememberImeState
import com.example.trustvault.presentation.viewmodels.onboarding.RegisterViewModel
import com.example.trustvault.presentation.viewmodels.onboarding.SMSAuthScreenViewModel
import kotlinx.coroutines.delay

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onGoBackClick: () -> Unit = {}, // Pass a lambda function with no return as a parameter
    onContinueClick: () -> Unit = {}
) {
    val darkTheme = viewModel.darkTheme
    val focusManager = LocalFocusManager.current // Handles where the current keyboard focus is
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val showBiometricPrompt = remember { mutableStateOf(false) }

    // Shared ViewModel to allow persistance of data between register and auth screen
    val activity = LocalContext.current as ComponentActivity
    val smsAuthViewModel: SMSAuthScreenViewModel = hiltViewModel(activity as ViewModelStoreOwner)

    var emailError by remember { mutableStateOf<String?>(null) } // remember will "remember" the value after recomposition
    // The by clause is syntactic sugar for:
    // var passwordError: String?
    // get() = passwordErrorState.value
    // set(value) { passwordErrorState.value = value }
    var passwordError by remember { mutableStateOf<String?>(null)}

    LaunchedEffect(key1= imeState.value) {
        if (imeState.value) {
            delay(100)
            scrollState.scrollTo(scrollState.maxValue)
        }
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background)
            .verticalScroll(scrollState)
            .padding(bottom = if(imeState.value) 200.dp else 0.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
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

        Spacer(Modifier.height(10.dp))

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
        Spacer(Modifier.height(60.dp))
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
                .fillMaxWidth()
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = {
                    viewModel.email = it
                    // Check email validity when it changes
                    emailError = if (!viewModel.isValidEmail(it)) "Please enter a valid email" else null
                },
                singleLine = true,
                label = { Text("E-Mail") },
                modifier = Modifier.fillMaxWidth(0.9f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions (onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                isError = emailError != null, // Show error state if there is an error
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF2F2F2),
                    unfocusedContainerColor = Color(0xFFF2F2F2),
                    disabledContainerColor = Color(0xFFF2F2F2),
                    errorContainerColor = Color(0xFFF2F2F2),
                    focusedLabelColor = if (darkTheme) Color.White else Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            )

            // Display the error message
            if (emailError != null) {
                Text(
                    text = emailError!!, // Not null assertion. Tells kotlin this variable cannot be null at this point
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(fontSize = 12.sp),
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            // Username Input
            OutlinedTextField(
                value = viewModel.username,
                onValueChange = { viewModel.username = it },
                singleLine = true,
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth(0.9f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF2F2F2),
                    unfocusedContainerColor = Color(0xFFF2F2F2),
                    disabledContainerColor = Color(0xFFF2F2F2),
                    focusedLabelColor = if (darkTheme) Color.White else Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            )

            var selectedCountryState by remember { mutableStateOf<CountryDetails?>(null)}
            var isMobileCorrect by remember { mutableStateOf(true) }
            val countryDialingCodes = mapOf(
                "AR" to "+54",  // Argentina
                "AU" to "+61",  // Australia
                "BR" to "+55",  // Brazil
                "CA" to "+1",   // Canada
                "CN" to "+86",  // China
                "FR" to "+33",  // France
                "DE" to "+49",  // Germany
                "IN" to "+91",  // India
                "ID" to "+62",  // Indonesia
                "IT" to "+39",  // Italy
                "JP" to "+81",  // Japan
                "MX" to "+52",  // Mexico
                "NL" to "+31",  // Netherlands
                "NG" to "+234", // Nigeria
                "PL" to "+48",  // Poland
                "RU" to "+7",   // Russia
                "ZA" to "+27",  // South Africa
                "KR" to "+82",  // South Korea
                "ES" to "+34",  // Spain
                "GB" to "+44",  // United Kingdom
                "US" to "+1"    // United States
            )

            CountryPickerOutlinedTextField(
                mobileNumber = CountryPickerUtils.getFormattedMobileNumber(
                    viewModel.phone, selectedCountryState?.countryCode ?: "ES"
                ),
                onMobileNumberChange = {
                    viewModel.phone = it
                    smsAuthViewModel.phone = it
                    var parsedPhoneNumber = countryDialingCodes.getValue(selectedCountryState?.countryCode.toString().uppercase()) + it
                    smsAuthViewModel.parsedPhoneNumber = parsedPhoneNumber
                    Log.d("PARSED PHONE NUMBER", parsedPhoneNumber)
                    Log.d("COUNTRY CODE", selectedCountryState?.countryCode.toString())
                    isMobileCorrect = viewModel.validatePhoneNumber(selectedCountryState?.countryCode, viewModel.phone)
               },
                onCountrySelected = {
                    selectedCountryState = it
                },
                singleLine = true,
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(0.9f),
                colors = androidx.compose.material3.TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF2F2F2),
                    unfocusedContainerColor = Color(0xFFF2F2F2),
                    disabledContainerColor = Color(0xFFF2F2F2),
                    focusedLabelColor = if (darkTheme) Color.White else Color.Black
                ),
                shape = RoundedCornerShape(12.dp),
                defaultCountryCode = "ES", // Default to Spain
                countriesList = listOf(
                    "AR", // Argentina
                    "AU", // Australia
                    "BR", // Brazil
                    "CA", // Canada
                    "CN", // China
                    "FR", // France
                    "DE", // Germany
                    "IN", // India
                    "ID", // Indonesia
                    "IT", // Italy
                    "JP", // Japan
                    "MX", // Mexico
                    "NL", // Netherlands
                    "NG", // Nigeria
                    "PL", // Poland
                    "RU", // Russia
                    "ZA", // South Africa
                    "KR", // South Korea
                    "ES", // Spain
                    "GB", // United Kingdom
                    "US"  // United States
                ),
                countryListDisplayType = CountryListDisplayType.BottomSheet, // Adjust the country display type to Dialog or any other style
                isError = !isMobileCorrect,
                supportingText = if (!isMobileCorrect) {
                    { Text(text = "El número de teléfono no es válido")}
                } else null,
                visualTransformation = VisualTransformation.None,
                maxLines = 1,
                minLines = 1,
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Password Input
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = {
                    viewModel.password = it
                    passwordError = viewModel.validatePassword(it)
                },
                singleLine = true,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.9f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                isError = passwordError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF2F2F2),
                    unfocusedContainerColor = Color(0xFFF2F2F2),
                    disabledContainerColor = Color(0xFFF2F2F2),
                    errorContainerColor = Color(0xFFF2F2F2),
                    focusedLabelColor = if (darkTheme) Color.White else Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            )

            // Display the error message
            if (passwordError != null) {
                Text(
                    text = passwordError!!, // Not null assertion. Tells kotlin this variable cannot be null at this point
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(fontSize = 12.sp),
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            // Confirm password Input
            OutlinedTextField(
                value = viewModel.confirmPassword,
                onValueChange = { viewModel.confirmPassword = it },
                singleLine = true,
                label = { Text("Confirma tu contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.9f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus() // hides the keyboard
                }),
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

        val context = LocalContext.current

        // Continue Button
        Button (
            onClick = {
                showBiometricPrompt.value = true
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
        if(showBiometricPrompt.value) {
            val cipher = remember { viewModel.initializeCipher() }
            BiometricRegisterScreen(
                cryptoObject = androidx.biometric.BiometricPrompt.CryptoObject(cipher),
                onSuccess = { authorizedCipher ->
                    viewModel.register(authorizedCipher)
                    smsAuthViewModel.authorizeUser(context, smsAuthViewModel.phone)
                    onContinueClick()
                    showBiometricPrompt.value = false }
            )
            smsAuthViewModel.authorizeUser(context, smsAuthViewModel.phone)
            onContinueClick()
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}