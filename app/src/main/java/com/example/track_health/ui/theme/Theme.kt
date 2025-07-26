package com.example.track_health.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme.colorScheme

@Composable
fun TrackHealthTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme, // use custom fixed scheme
        typography = Typography(),
        content = content
    )
}
