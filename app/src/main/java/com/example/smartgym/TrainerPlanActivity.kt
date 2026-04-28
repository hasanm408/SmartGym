package com.example.smartgym

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgym.model.DayDiet
import com.example.smartgym.model.DayWorkout
import com.example.smartgym.model.Exercise
import com.example.smartgym.model.TrainerPlan

class TrainerPlanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_plan)

        val workouts = listOf("Chest", "Back", "Legs", "Shoulders", "Biceps", "Triceps", "Abs")
        val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

        val spinnerUser = findViewById<Spinner>(R.id.spinnerUser)
        val spinnerMap = mapOf(
            "Monday" to findViewById<Spinner>(R.id.spinnerMonday),
            "Tuesday" to findViewById<Spinner>(R.id.spinnerTuesday),
            "Wednesday" to findViewById<Spinner>(R.id.spinnerWednesday),
            "Thursday" to findViewById<Spinner>(R.id.spinnerThursday),
            "Friday" to findViewById<Spinner>(R.id.spinnerFriday),
            "Saturday" to findViewById<Spinner>(R.id.spinnerSaturday),
            "Sunday" to findViewById<Spinner>(R.id.spinnerSunday)
        )
        val etPlanTime = findViewById<EditText>(R.id.etPlanTime)
        val btnAssign = findViewById<Button>(R.id.btnAssignPlan)

        spinnerUser.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            AppData.users.map { it.name }
        )
        spinnerMap.values.forEach { spinner ->
            spinner.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                workouts
            )
        }

        btnAssign.setOnClickListener {
            val selectedUser = spinnerUser.selectedItem?.toString().orEmpty()
            if (selectedUser.isBlank()) {
                Toast.makeText(this, "Select a client first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rawPlan = days.associateWith { day -> spinnerMap[day]?.selectedItem.toString() }
            val structuredWorkout: Map<String, DayWorkout> = buildStructuredWorkoutPlan(rawPlan)
            val structuredDiet = buildWeeklyDietPlan()
            val planTime = etPlanTime.text.toString().trim().ifBlank { "6:00 PM" }
            AppData.trainerPlan = TrainerPlan(
                workoutPlan = structuredWorkout,
                dietPlan = structuredDiet,
                time = planTime
            )
            AppData.ensureUserExists(selectedUser)

            Toast.makeText(this, "Plan assigned to $selectedUser", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun buildStructuredWorkoutPlan(rawPlan: Map<String, String>): Map<String, DayWorkout> {
        return rawPlan.mapValues { (_, workoutType) ->
            val exercises = when (workoutType) {
                "Chest" -> listOf(Exercise("Bench Press", 3, 12), Exercise("Incline Press", 3, 12))
                "Back" -> listOf(Exercise("Pull Ups", 3, 10), Exercise("Deadlift", 3, 8))
                "Legs" -> listOf(Exercise("Squats", 4, 10), Exercise("Leg Press", 3, 12))
                "Shoulders" -> listOf(Exercise("Overhead Press", 3, 10), Exercise("Lateral Raise", 3, 15))
                "Biceps" -> listOf(Exercise("Barbell Curl", 3, 12), Exercise("Hammer Curl", 3, 12))
                "Triceps" -> listOf(Exercise("Skullcrusher", 3, 12), Exercise("Pushdowns", 3, 15))
                "Abs" -> listOf(Exercise("Crunches", 3, 20), Exercise("Plank", 3, 60))
                else -> emptyList()
            }
            DayWorkout(exercises)
        }
    }

    private fun buildWeeklyDietPlan(): Map<String, DayDiet> {
        val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        return days.associateWith {
            DayDiet(
                breakfast = "Oats with protein scoop",
                lunch = "Grilled chicken with brown rice",
                dinner = "Salmon with steamed vegetables",
                snacks = "Greek yogurt or handful of almonds"
            )
        }
    }
}
