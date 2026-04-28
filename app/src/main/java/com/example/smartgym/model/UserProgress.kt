package com.example.smartgym.model

data class UserProgress(
    val userName: String,
    var completedExercises: Int,
    var completedMeals: Int,
    var streak: Int
)
