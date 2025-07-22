package com.example.track_health.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
