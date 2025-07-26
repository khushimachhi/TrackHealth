package com.example.track_health

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.track_health.ui.MainScreen
import com.example.track_health.ui.screens.AddHabitScreen
import com.example.track_health.ui.screens.HabitScreen
import com.example.track_health.viewmodel.HabitViewModel

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object HabitList : Screen("habit_list")
    object AddHabit : Screen("add_habit")
}

@Composable
fun TrackHealthNavGraph(viewModel: HabitViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            val allHabits by viewModel.habits.collectAsState(initial = emptyList())
            MainScreen(
                navController = navController,
                habits = allHabits,
                onHabitClick = { habit ->
                    // optionally handle click on habit card
                },
                onAddHabitClick = {
                    navController.navigate(Screen.AddHabit.route)
                },
                onCompletedHabitsClick = {
                    navController.navigate(Screen.HabitList.route) // if you still want to use HabitScreen
                }
            )
        }

        composable(Screen.AddHabit.route) {
            AddHabitScreen(
                viewModel = viewModel,
                onHabitAdded = {
                    navController.popBackStack() // navigates back to Main
                }
            )
        }

        composable(Screen.HabitList.route) {
            HabitScreen(viewModel = viewModel, onAddClick = {
                navController.navigate(Screen.AddHabit.route)
            })
        }
    }

}
