package com.example.trustvault.presentation.utils

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.trustvault.presentation.theme.DarkModePrimaryGradient
import com.example.trustvault.presentation.theme.LightModePrimaryGradient

@Composable
fun GradientTrackSwitch(
    darkTheme: Boolean,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val trackWidth = 45.dp
    val trackHeight = 25.dp
    val thumbSize = 17.dp

    val thumbOffset by animateDpAsState(
        if (checked) trackWidth - thumbSize - 7.dp else 1.dp, // offset before the else is the right margin, offset after else is left margin of the thumb
        label = "thumbOffset"
    )

    Box(
        modifier = Modifier
            .width(trackWidth)
            .height(trackHeight)
            .clip(RoundedCornerShape(50))
            .background(
                if (checked)
                    if (darkTheme) DarkModePrimaryGradient else LightModePrimaryGradient
                else
                    Brush.linearGradient(
                        colors = listOf(Color.DarkGray, Color.Gray)
                    )
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .offset(x = thumbOffset)
                .size(thumbSize)
                .background(Color.White, shape = CircleShape)
        )
    }
}
