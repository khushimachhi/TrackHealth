package com.example.track_health.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.track_health.data.entities.Habit
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

import java.util.*

@Composable
fun MainScreen(
    navController: NavController,
    habits: List<Habit>,
    onHabitClick: (Habit) -> Unit,
    onAddHabitClick: () -> Unit,
    onCompletedHabitsClick: () -> Unit
) {
    val today = remember { LocalDate.now() }
    var selectedDate by remember { mutableStateOf(today) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Calendar Row
        CalendarBar(selectedDate = selectedDate, onDateSelected = { selectedDate = it })

        Spacer(modifier = Modifier.height(16.dp))

        // Habit List
        HabitList(habits = habits.filter { !it.isCompletedToday }, onHabitClick = onHabitClick)

        Spacer(modifier = Modifier.height(16.dp))

        // Steps & Water Row
        SummaryRow(steps = 4491, stepGoal = 3500, water = 1000, waterGoal = 1000)

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons
        ActionButtons(
            onAddHabitClick = onAddHabitClick,
            onCompletedHabitsClick = onCompletedHabitsClick
        )
    }
}

@Composable
fun CalendarBar(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val days = (0..6).map { LocalDate.now().minusDays((3 - it).toLong()) }
    LazyRow {
        items(days) { date ->
            val isSelected = date == selectedDate
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDateSelected(date) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                )
                Text(
                    text = date.dayOfMonth.toString(),
                    fontSize = 18.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun HabitList(habits: List<Habit>, onHabitClick: (Habit) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        habits.forEach { habit ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onHabitClick(habit) },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = habit.name, fontWeight = FontWeight.Bold)
                        Text(text = "${habit.currentValue}/${habit.targetValue} ${habit.unit}")

                    }
                    Text(text = "🔥 ${habit.streakCount} Days")
                }
            }
        }
    }
}

@Composable
fun SummaryRow(steps: Int, stepGoal: Int, water: Int, waterGoal: Int) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Steps")
            Text(text = "$steps/$stepGoal")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Water")
            Text(text = "$water/$waterGoal ml")
        }
    }
}

@Composable
fun ActionButtons(onAddHabitClick: () -> Unit, onCompletedHabitsClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onCompletedHabitsClick) {
            Text("View Completed")
        }
        Button(onClick = onAddHabitClick) {
            Text("Add Habit")
        }
        // Add a third button if needed
    }
}
