package com.example.trustvault

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SMSAuthActivity {
    @Composable
    @Preview
    // TODO: As soon as this screen is painted, send a code to the users phone number and check it automatically, if possible
    fun SMSAuthScreen() {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF111111)),
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
                text = "El código ha sido enviado al 674 547 145", // TODO: Bring phone number from the previous form/saved number from the user
                fontSize = 18.sp,
                color = Color(0xFFB2B2B2)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Resend code button
            Text(
                text = "Reenviar el código en 00:30", // TODO: Start a 30 sec countdown, replace with a resend button once it finishes
                fontSize = 18.sp,
                color = Color(0xFFF9F9F9)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) { repeat(5) { // Repeat allows us to paint x times the elements that are inside the statement. Avoids code repetition
                // Gradient brush to call it from the Canvas
                val gradientBrush = Brush.linearGradient(
                    colors = listOf(Color(0xFF5D1D6C), Color(0xFF5B3C93), Color(0xFF5A69CB), Color(0xFF921BAF))
                )

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
                }
                }
            }
        }
    }
}