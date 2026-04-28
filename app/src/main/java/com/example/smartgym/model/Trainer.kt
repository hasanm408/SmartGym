package com.example.smartgym.model

import java.io.Serializable

data class Trainer(
    val name: String,
    val email: String,
    val password: String,
    val age: Int,
    val specialization: String,
    val experience: String,
    val time: String,
    val availableDays: List<String>,
    val workoutPlan: Map<String, DayWorkout> = emptyMap(),
    val dietPlan: Map<String, DayDiet> = emptyMap()
) : Serializable
