package com.example.track_health.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val currentValue: Int,
    val targetValue: Int,
    val unit: String,
    val frequency: Int,
    val reminderTime: String,
    val isCompletedToday: Boolean = false,
    val streakCount: Int = 0,
    val lastCompletedDate: String = ""
)
