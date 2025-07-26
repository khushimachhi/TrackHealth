package com.example.track_health.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val LightBlue = Color(0xFFDDEEFF)
val LightSurface = Color(0xFFF8FAFF)
val PrimaryColor = Color(0xFF1565C0)
val OnPrimary = Color.White
val Secondary = Color(0xFF90CAF9)
val OnSecondary = Color.Black

val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    background = LightSurface,
    surface = LightSurface,
    onSurface = Color.Black,
    surfaceVariant = LightBlue
)
