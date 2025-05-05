package com.example.trustvault.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.min
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.trustvault.R
import com.example.trustvault.presentation.theme.DarkColorScheme
import com.example.trustvault.presentation.theme.LightColorScheme
import com.example.trustvault.presentation.viewmodels.onboarding.LoaderScreenViewModel

/**
 * LoaderScreen is a composable that shows a loading animation and a success message
 * after login or registration. It also handles navigation after a delay.
 *
 * @param viewModel The [LoaderScreenViewModel] that provides theme and navigation logic.
 * @param goToMainScreenAfterWait Lambda to be invoked when it's time to navigate to the main screen.
 *
 * @author David Pires Manzanares
 */
@Composable
fun LoaderScreen(
    viewModel: LoaderScreenViewModel = hiltViewModel(),
    goToMainScreenAfterWait: () -> Unit
) {
    val darkTheme = viewModel.darkTheme
    val navigateToNextScreen by viewModel.navigateToNextScreen.collectAsState()

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val imageSize = min(screenHeight, screenWidth) * 0.8f

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ok_animation))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    // Trigger loading logic when the composable is first launched
    LaunchedEffect(Unit) {
        viewModel.waitForSeconds()
    }

    // Once navigation flag is true, perform the navigation
    LaunchedEffect(navigateToNextScreen) {
        if (navigateToNextScreen) {
            goToMainScreenAfterWait()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(250.dp)
                .scale(1.5f)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Terminado!",
                style = TextStyle(
                    color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 100.dp, bottom = 32.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Ya puedes empezar a crear y guardar contraseñas de forma segura",
                style = TextStyle(
                    color = if (darkTheme) DarkColorScheme.onBackground else LightColorScheme.onBackground,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(horizontal = 60.dp, vertical = 20.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LoaderIcon()
        }
    }
}

/**
 * LoaderIcon displays a circular loading animation using a canvas and custom animation logic.
 *
 * @param size The size of the loader indicator.
 * @param sweepAngle The angle covered by the spinning arc.
 * @param color The color of the spinning arc.
 * @param strokeWidth The stroke width of both the background circle and the spinning arc.
 *
 * @author David Pires Manzanares
 */
@Composable
fun LoaderIcon(
    size: Dp = 60.dp,
    sweepAngle: Float = 90f,
    color: Color = MaterialTheme.colors.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth
) {
    // Infinite animation that updates the start angle of the arc
    val transition = rememberInfiniteTransition()

    val currentArcStartAngle by transition.animateValue(
        initialValue = 0,
        targetValue = 360,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing)
        )
    )

    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
    }

    // Draw the loader using Canvas
    Canvas(
        modifier = Modifier
            .size(size)
            .padding(strokeWidth / 2)
    ) {
        // Draw base circle
        drawCircle(Color.LightGray, style = stroke)

        // Draw animated arc
        drawArc(
            color = color,
            startAngle = currentArcStartAngle.toFloat() - 90,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = stroke
        )
    }
}
