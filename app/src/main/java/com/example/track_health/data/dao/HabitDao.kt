package com.example.track_health.data.dao

import androidx.room.*
import com.example.track_health.data.entities.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habit ORDER BY id DESC")
    fun getAllHabits(): Flow<List<Habit>>

    @Update
    suspend fun updateHabit(habit: Habit)

    @Query("UPDATE habit SET isCompletedToday = :isCompleted, streakCount = :streak WHERE id = :habitId")
    suspend fun updateCompletionStatus(habitId: Int, isCompleted: Boolean, streak: Int)
}
