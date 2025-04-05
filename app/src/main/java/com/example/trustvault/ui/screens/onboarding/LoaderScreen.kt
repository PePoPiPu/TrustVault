package com.example.trustvault.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trustvault.R
import com.example.trustvault.ui.theme.DarkColorScheme
import com.example.trustvault.ui.theme.LightColorScheme
import com.example.trustvault.ui.viewmodels.LoaderScreenViewModel


@Composable
fun LoaderScreen(
    viewModel: LoaderScreenViewModel = hiltViewModel(),
    goToMainScreenAfterWait: () -> Unit // Lambda function for navigation
) {
    val darkTheme = viewModel.darkTheme
    val navigateToNextScreen by viewModel.navigateToNextScreen.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.waitForSeconds() // Call function as soon as UI loads
    }

    LaunchedEffect(navigateToNextScreen) {
        if (navigateToNextScreen) {
            goToMainScreenAfterWait() // Trigger navigation once TODO: Substitute to a function in viewmodel that waits for everything to load!!!!
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(if (darkTheme) DarkColorScheme.surface else LightColorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ok),
            contentDescription = "Successful login/register logo",
            modifier = Modifier
                .size(400.dp)
                .padding(top = 60.dp)
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

@Composable
fun LoaderIcon(
    size: Dp = 60.dp, // indicator size
    sweepAngle: Float = 90f, // angle (lenght) of indicator arc
    color: Color = MaterialTheme.colors.primary, // color of indicator arc line
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth //width of cicle and ar lines
) {
    ////// animation //////

    val transition = rememberInfiniteTransition()

    // define the changing value from 0 to 360.
    // This is the angle of the beginning of indicator arc
    // this value will change over time from 0 to 360 and repeat indefinitely.
    // it changes starting position of the indicator arc and the animation is obtained
    val currentArcStartAngle by transition.animateValue(
        0,
        360,
        Int.VectorConverter,
        infiniteRepeatable(
            animation = tween(
                durationMillis = 1100,
                easing = LinearEasing
            )
        )
    )

    ////// draw /////

    // define stroke with given width and arc ends type considering device DPI
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
    }

    // draw on canvas
    Canvas(
        Modifier
            .size(size) // canvas size
            .padding(strokeWidth / 2) //padding. otherwise, not the whole circle will fit in the canvas
    ) {
        // draw background (gray) circle with defined stroke.
        // without explicit center and radius it fit canvas bounds
        drawCircle(Color.LightGray, style = stroke)

        // draw arc with the same stroke
        drawArc(
            color,
            // arc start angle
            // -90 shifts the start position towards the y-axis
            startAngle = currentArcStartAngle.toFloat() - 90,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = stroke
        )
    }
}