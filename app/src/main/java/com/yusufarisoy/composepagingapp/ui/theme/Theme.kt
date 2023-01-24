package com.yusufarisoy.composepagingapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Blue900,
    primaryVariant = Blue700,
    secondary = Teal200,
    background = White
)

private val LightColorPalette = lightColors(
    primary = Blue900,
    primaryVariant = Blue700,
    secondary = Teal200
)

@Composable
fun ComposePagingAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
