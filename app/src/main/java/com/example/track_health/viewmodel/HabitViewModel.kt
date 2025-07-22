package com.example.track_health.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.track_health.data.entities.Habit
import com.example.track_health.data.repository.HabitRepository
import kotlinx.coroutines.launch

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
