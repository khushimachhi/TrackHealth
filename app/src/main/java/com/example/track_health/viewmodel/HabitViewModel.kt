package com.example.track_health.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.track_health.data.entities.Habit
import com.example.track_health.data.repository.HabitRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate


class HabitViewModel(private val repository: HabitRepository) : ViewModel() {

    val habits = repository.allHabits

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            repository.insertHabit(habit)
        }
    }

    fun removeHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }

    fun markHabitAsDone(habit: Habit) {
        val today = LocalDate.now().toString()
        val yesterday = LocalDate.now().minusDays(1).toString()
        val updatedStreak = when (habit.lastCompletedDate) {
            yesterday -> habit.streakCount + 1
            today -> habit.streakCount
            else -> 1
        }

        val updatedHabit = habit.copy(
            isCompletedToday = true,
            streakCount = updatedStreak,
            lastCompletedDate = today
        )

        viewModelScope.launch {
            repository.updateHabit(updatedHabit)
        }
    }


    fun toggleHabitCompletion(habit: Habit) {
        viewModelScope.launch {
            val updatedHabit = if (!habit.isCompletedToday) {
                habit.copy(
                    isCompletedToday = true,
                    streakCount = habit.streakCount + 1
                )
            } else {
                habit.copy(
                    isCompletedToday = false,
                    streakCount = maxOf(0, habit.streakCount - 1)
                )
            }
            repository.updateHabit(updatedHabit)
        }
    }

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habit: StateFlow<List<Habit>> = _habits

    private val _showCompleted = MutableStateFlow(false)
    val showCompleted: StateFlow<Boolean> = _showCompleted


    fun toggleShowCompleted() {
        _showCompleted.value = !_showCompleted.value
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }


}

class HabitViewModelFactory(private val repository: HabitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
