package com.example.track_health.ui.screens

import androidx.compose.runtime.*
import com.example.track_health.viewmodel.HabitViewModel

@Composable
fun MainScreen(viewModel: HabitViewModel) {
    var currentScreen by remember { mutableStateOf("list") }

    when (currentScreen) {
        "list" -> HabitScreen(viewModel) {
            currentScreen = "add"
        }
        "add" -> AddHabitScreen(viewModel) {
            currentScreen = "list"
        }
    }
}
