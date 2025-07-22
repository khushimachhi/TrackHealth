package com.example.track_health.data.repository

import com.example.track_health.data.dao.HabitDao
import com.example.track_health.data.entities.Habit
import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {
    val allHabits: Flow<List<Habit>> = habitDao.getAllHabits()

    suspend fun insertHabit(habit: Habit) = habitDao.insertHabit(habit)

    suspend fun deleteHabit(habit: Habit) = habitDao.deleteHabit(habit)



}
