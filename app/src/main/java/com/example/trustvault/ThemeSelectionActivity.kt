package com.example.trustvault

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ThemeSelectionActivity {

    /**
     * This composable function displays a screen allowing users to choose between light and dark modes
     *
     * Included in the screen:
     * - A title prompting the user to choose a theme.
     * - A brief description of the dark theme.
     * - An illustration representing dark mode.
     * - A toggle button to switch between dark and light themes.
     * - A button to continue to the next screen (e.g., SMS authentication).
     *
     * @param darkTheme A boolean indicating whether the current theme is dark (true) or light (false).
     * @param onThemeUpdated A callback function that is invoked when the theme is updated. It returns nothing (Unit == void in Java)
     *
     * @author Alex Álvarez de Sotomayor Sugimoto
     */
    @Composable
    fun ThemeSelectionScreen(darkTheme: Boolean, onThemeUpdated: () -> Unit){

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
                text = if (darkTheme) "Modo Oscuro" else "Modo Claro",
                color = if (darkTheme) Color.White else Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(40.dp))

            ThemeSwitcher (
                darkTheme = darkTheme,
                onClick = onThemeUpdated,
                size = 75.dp,
                padding = 5.dp
            )

            Spacer(modifier = Modifier.height(30.dp))

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
    /**
     * Composable function that displays a toggle switch to switch between dark and light themes.
     *
     * This switch includes:
     * - A background that changes color based on the selected theme.
     * - A movable toggle that animates between the dark and light theme positions.
     * - Icons representing the dark and light modes.
     * - A callback that is triggered when the switch is clicked.
     *
     * @param darkTheme A boolean indicating whether the current theme is dark (true) or light (false).
     * @param size The size of the entire switch component.
     * @param iconSize The size of the icons (moon and sun) inside the toggle.
     * @param padding Padding around the circle component inside.
     * @param borderWidth The width of the border around the switch.
     * @param parentShape The shape of the switch container (a circle as default).
     * @param toggleShape The shape of the toggle inside the switch (a circle as default).
     * @param animationSpec The animation spec for the toggle's movement (default is a tween with 300ms duration).
     * @param onClick A callback function that is invoked when the switch is clicked to change the theme.
     *
     * @author Alex Álvarez de Sotomayor Sugimoto
     */
    @Composable
    fun ThemeSwitcher(
        darkTheme: Boolean = false,
        size: Dp = 150.dp,
        iconSize: Dp = size / 3,
        padding: Dp = 10.dp,
        borderWidth: Dp = 1.dp,
        parentShape: Shape = CircleShape,
        toggleShape: Shape = CircleShape,
        animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300), // Tween stands for tween animation AKA generating frames between 2 keyframes. Keyframes are set by the offset later.
        onClick: () -> Unit
    ) {
        val offset by animateDpAsState(
            targetValue = if (darkTheme) 0.dp else size,
            animationSpec = animationSpec
        )

        // This is the actual container of the switch
        Box(modifier = Modifier
            .width(size * 2) // Multiply by 2 since we have to icons
            .height(size)
            .clip(shape = parentShape)
            .clickable { onClick() }
            .background(if (darkTheme) Color(0XFF171717) else Color.White)
        ) {
            // This is the toggle that we move with the tween animation
            Box(
                modifier = Modifier
                    .size(size)
                    .offset(x = offset)
                    .padding(all = padding)
                    .clip(shape = toggleShape)
                    .background(if (darkTheme) Color(0xFF282D37) else Color(0xFF5F74FF))
            ) {}
            Row(
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            width = borderWidth,
                            color = Color(0xFF282D37)
                        ),
                        shape = parentShape
                    )
            ) {
                // Container for the moon icon
                Box(
                    modifier = Modifier.size(size),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = R.drawable.ic_moon),
                        contentDescription = "Dark Mode",
                        tint = if (darkTheme) MaterialTheme.colorScheme.secondaryContainer else Color(0xFF171717)
                    )
                }

                // Container for the sun icon
                Box(
                    modifier = Modifier.size(size),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = R.drawable.ic_sun),
                        contentDescription = "Light Mode",
                        tint = MaterialTheme.colorScheme.secondaryContainer
                    )
                }
            }
        }
    }

    /**
     * This function is used purely for previewing
     */
    @Preview
    @Composable
    fun PreviewThemeSelectionScreen()
    {
        var darkTheme by remember { mutableStateOf(false) } // This is to be set in the main activity. Set here for testing
        ThemeSelectionScreen(
            darkTheme = !darkTheme,
            onThemeUpdated = {darkTheme = !darkTheme}
        )
    }
}