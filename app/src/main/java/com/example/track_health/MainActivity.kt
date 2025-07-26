package com.example.track_health

import com.example.track_health.TrackHealthNavGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.track_health.data.database.HabitDatabase
import com.example.track_health.data.repository.HabitRepository
import com.example.track_health.data.entities.Habit
import com.example.track_health.ui.screens.HabitScreen
import com.example.track_health.ui.theme.TrackHealthTheme
import com.example.track_health.viewmodel.HabitViewModel
import com.example.track_health.viewmodel.HabitViewModelFactory
import kotlinx.coroutines.launch

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




        setContent {
            TrackHealthTheme {
                TrackHealthNavGraph(viewModel = viewModel)
            }
        }


    }
}
