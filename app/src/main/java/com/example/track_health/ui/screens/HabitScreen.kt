package com.example.track_health.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.track_health.data.entities.Habit
import com.example.track_health.viewmodel.HabitViewModel
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch


@Composable
fun HabitItem(
    habit: Habit,
    onToggleComplete: (Habit) -> Unit,
    onDelete: (Habit) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = habit.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "🔥 Streak: ${habit.streakCount} days", style = MaterialTheme.typography.bodySmall)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = habit.isCompletedToday,
                    onCheckedChange = { onToggleComplete(habit) }
                )
                if (habit.isCompletedToday) {
                    IconButton(onClick = { onDelete(habit) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Habit"
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun HabitScreen(viewModel: HabitViewModel, onAddClick: () -> Unit) {
    val allHabits by viewModel.habits.collectAsState(initial = emptyList())
    val showCompleted by viewModel.showCompleted.collectAsState()

    val filteredHabits = if (showCompleted) {
        allHabits
    } else {
        allHabits.filter { !it.isCompletedToday }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(3.dp),
                shape = RoundedCornerShape(50.dp),
                content = {
                    Text("🌱")
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Show Completed", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = showCompleted,
                    onCheckedChange = { viewModel.toggleShowCompleted() }
                )
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(filteredHabits) { habit ->
                    HabitItem(
                        habit = habit,
                        onToggleComplete = { viewModel.markHabitAsDone(it) },
                        onDelete = {
                            scope.launch {
                                viewModel.deleteHabit(it)
                                snackbarHostState.showSnackbar("✅ Habit marked as completed!")
                            }


                }
                    )
                }
            }
        }
    }
}




