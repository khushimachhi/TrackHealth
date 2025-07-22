package com.example.track_health.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.track_health.data.entities.Habit
import com.example.track_health.data.repository.HabitRepository
import com.example.track_health.viewmodel.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitScreen(viewModel: HabitViewModel, onAddClick: () -> Unit) {
    val habits by viewModel.habits.collectAsState(initial = emptyList()) // ✅ NEW


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Filled.Add, contentDescription = "Add Habit")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(habits) { habit ->
                HabitItem(habit)
            }
        }

    }
}


@Composable
fun HabitItem(habit: Habit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(habit.name, style = MaterialTheme.typography.titleMedium)
            Text("Category: ${habit.category}")
            Text("Reminder: ${habit.reminderTime}")
        }
    }
}
