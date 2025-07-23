package com.example.track_health.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.track_health.data.entities.Habit
import com.example.track_health.viewmodel.HabitViewModel

@Composable
fun AddHabitScreen(viewModel: HabitViewModel, onHabitAdded: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var targetValueInput by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add New Habit", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Habit Name") }
        )

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") }
        )

        OutlinedTextField(
            value = reminderTime,
            onValueChange = { reminderTime = it },
            label = { Text("Reminder Time (e.g., 08:00 AM)") }
        )

        OutlinedTextField(
            value = frequency,
            onValueChange = { frequency = it },
            label = { Text("Frequency per Day") },
            singleLine = true
        )

        OutlinedTextField(
            value = targetValueInput,
            onValueChange = { targetValueInput = it },
            label = { Text("Target Value") },
            singleLine = true
        )

        OutlinedTextField(
            value = unit,
            onValueChange = { unit = it },
            label = { Text("Unit (e.g., steps, glasses)") },
            singleLine = true
        )



        Button(
            onClick = {    val targetValue = targetValueInput.toIntOrNull() ?: 0
                val habit = Habit(
                    id = 0,
                    name = name,
                    category = category,
                    currentValue = 0,
                    targetValue = targetValue,
                    unit = unit,
                    frequency = frequency.toIntOrNull() ?: 1,
                    reminderTime = reminderTime
                )
                viewModel.addHabit(habit)
                onHabitAdded()
            }
        ) {
            Text("Add Habit")
        }
    }
}
