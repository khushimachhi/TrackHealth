package com.example.track_health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.track_health.ui.theme.TrackHealthTheme
import com.example.track_health.data.database.HabitDatabase
import com.example.track_health.data.repository.HabitRepository
import com.example.track_health.ui.screens.HabitScreen
import com.example.track_health.viewmodel.HabitViewModel
import com.example.track_health.viewmodel.HabitViewModelFactory

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.track_health.data.entities.Habit
import com.example.track_health.ui.screens.MainScreen

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Room DB & Repository
        val db = HabitDatabase.getDatabase(applicationContext)
        val repository = HabitRepository(db.habitDao())

        // Initialize ViewModel
        val viewModelFactory = HabitViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HabitViewModel::class.java]




        // Inside onCreate, after viewModel initialization:
        lifecycleScope.launch {
            viewModel.addHabit(
                Habit(
                    id = 0, // assuming autoGenerate = true in your @Entity
                    name = "Drink Water",
                    category = "Health",
                    reminderTime = "08:00 AM",
                    frequency = 1 // or any Int value that makes sense
                )
            )

        }

        // Set Compose Content
        setContent {
            TrackHealthTheme {
                MainScreen(viewModel = viewModel)
            }
        }


    }
}
