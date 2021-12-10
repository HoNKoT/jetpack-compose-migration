package com.example.jetpackcomposemigration.ext

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun CustomTheme() {
}

val DarkColors = lightColors(
    primary = Color.Black,
    secondary = Color.Gray,
    secondaryVariant = Color.LightGray,
)

val LightColors = darkColors(
    primary = Color.White,
    secondary = Color.LightGray,
    secondaryVariant = Color.DarkGray,
)
