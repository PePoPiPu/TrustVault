package com.example.trustvault

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class RegisterActivity {
    @Composable // Composable object
    @Preview
    fun RegisterScreen() {
        Column (
            modifier = Modifier // Create this column and the attributes
                .fillMaxSize() // Make the layout fill all available space
                .background(Color(0xFF111111)),
            verticalArrangement = Arrangement.Center, // Vertical Alignment
            horizontalAlignment = Alignment.CenterHorizontally // Horizontal Alignment
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Queremos conocerte mejor",
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            listOf(Color(0xFFBC39DB), Color(0xFF7C8EFF), Color(0xFFE7A3F8))
                        ),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Left
            ) {
                Text(
                    text = "Informacion personal",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 50.dp)
                )

            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                TextField(
                    value="",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 50.dp, vertical = 10.dp),
                    placeholder = {
                    Text(
                        text = "email"
                    )
                }

                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                TextField(
                    value="",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 50.dp, vertical = 10.dp),
                    placeholder = {
                        Text(
                            text = "username"
                        )
                    }

                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                TextField(
                    value="",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 50.dp, vertical = 10.dp),
                    placeholder = {
                        Text(
                            text = "Phone number"
                        )
                    }

                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                TextField(
                    value="",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 50.dp, vertical = 10.dp),
                    placeholder = {
                        Text(
                            text = "Password"
                        )
                    }

                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                TextField(
                    value="",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 50.dp, vertical = 10.dp),
                    placeholder = {
                        Text(
                            text = "Repeat password"
                        )
                    }

                )
            }

            Button(
                onClick = { /* Registrar usuario */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 50.dp, vertical = 20.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF9C27B0), Color(0xFF673AB7))
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Registrar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

}