package com.example.track_health.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
        .padding(35.dp)) {

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
    LazyRow(horizontalArrangement = Arrangement.SpaceEvenly) {
        items(days) { date ->
            val isSelected = date == selectedDate
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onDateSelected(date) }
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp),
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
                    fontSize = 16.sp,
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
                    .padding(vertical = 6.dp)
                    .clickable { onHabitClick(habit) },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = habit.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = Color(0xFF0D47A1)
                        )
                        Text(
                            text = "${habit.currentValue}/${habit.targetValue} ${habit.unit}",
                            color = Color.DarkGray,
                            fontSize = 14.sp
                        )
                    }
                    Text(
                        text = "🔥 ${habit.streakCount} Days",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFF57C00)
                    )
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
fun SummaryCard(label: String, value: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(4.dp)
            .width(150.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = value, fontSize = 16.sp, color = Color.DarkGray)
        }
    }
}

@Composable
fun ActionButtons(onAddHabitClick: () -> Unit, onCompletedHabitsClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedButton(
            onClick = onCompletedHabitsClick,
            shape = RoundedCornerShape(50.dp)
        ) {
            Text("View Completed")
        }

        Button(
            onClick = onAddHabitClick,
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Add Habit")
        }
    }
}
