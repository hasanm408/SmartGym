package com.example.smartgym.model

import java.io.Serializable

data class Exercise(
    val name: String,
    val sets: Int,
    val reps: Int
) : Serializable

data class DayWorkout(
    val exercises: List<Exercise>
) : Serializable

data class DayDiet(
    val breakfast: String,
    val lunch: String,
    val dinner: String,
    val snacks: String = ""
) : Serializable

data class ExerciseLog(
    val exerciseName: String,
    var weight: String = "",
    var repsDone: String = "",
    var completed: Boolean = false
) : Serializable

data class WorkoutLog(
    val userName: String,
    val date: String, // yyyy-MM-dd
    val logs: MutableList<ExerciseLog> = mutableListOf(),
    var isCompleted: Boolean = false
) : Serializable

data class MealLog(
    val userName: String,
    val date: String,
    var breakfastDone: Boolean = false,
    var lunchDone: Boolean = false,
    var dinnerDone: Boolean = false
) : Serializable

data class Message(
    val senderName: String,
    val receiverName: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
) : Serializable

