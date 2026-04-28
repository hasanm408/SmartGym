package com.example.smartgym.model

data class TrainerPlan(
    val workoutPlan: Map<String, DayWorkout>,
    val dietPlan: Map<String, DayDiet>,
    val time: String
)
