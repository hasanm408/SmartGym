package com.example.smartgym

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WorkoutDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_detail)

        val level = intent.getStringExtra("level") ?: "Beginner"
        val muscleGroup = intent.getStringExtra("muscleGroup") ?: "Chest"

        val tvWorkoutTitle = findViewById<TextView>(R.id.tvWorkoutTitle)
        val tvWorkoutList = findViewById<TextView>(R.id.tvWorkoutList)

        tvWorkoutTitle.text = "$level - $muscleGroup Workouts"
        tvWorkoutList.text = getWorkoutPlan(level, muscleGroup)
    }

    private fun getWorkoutPlan(level: String, muscleGroup: String): String {
        val workoutMap = mapOf(
            "Beginner" to mapOf(
                "Chest" to listOf("Incline Push-ups (3x10)", "Knee Push-ups (3x8)", "Chest Stretch (2 min)"),
                "Back" to listOf("Superman Hold (3x15 sec)", "Resistance Band Pull (3x10)", "Light Rows (3x8)"),
                "Legs" to listOf("Bodyweight Squats (3x12)", "Lunges (3x10)", "Calf Raises (3x15)"),
                "Shoulders" to listOf("Arm Circles (3x15)", "Light Dumbbell Press (3x8)", "Front Raise (3x10)"),
                "Biceps" to listOf("Dumbbell Curls (3x10)", "Hammer Curls (3x8)", "Resistance Band Curl (3x10)"),
                "Triceps" to listOf("Bench Dips (3x8)", "Tricep Extensions (3x10)", "Overhead Dumbbell Extension (3x8)"),
                "Abs" to listOf("Crunches (3x15)", "Plank (3x20 sec)", "Leg Raises (3x10)")
            ),
            "Intermediate" to mapOf(
                "Chest" to listOf("Bench Press (4x8)", "Dumbbell Fly (3x10)", "Push-ups (3x15)"),
                "Back" to listOf("Pull-ups (3x8)", "Barbell Rows (3x10)", "Lat Pulldown (3x10)"),
                "Legs" to listOf("Squats (4x10)", "Deadlifts (3x8)", "Leg Press (3x12)"),
                "Shoulders" to listOf("Shoulder Press (3x10)", "Lateral Raise (3x12)", "Arnold Press (3x8)"),
                "Biceps" to listOf("Barbell Curl (3x10)", "Incline Dumbbell Curl (3x8)", "Concentration Curl (3x10)"),
                "Triceps" to listOf("Skull Crushers (3x10)", "Cable Pushdown (3x12)", "Dips (3x8)"),
                "Abs" to listOf("Hanging Leg Raise (3x10)", "Russian Twist (3x20)", "Plank (3x30 sec)")
            ),
            "Advanced" to mapOf(
                "Chest" to listOf("Incline Bench Press (4x8)", "Cable Fly (4x10)", "Weighted Push-ups (3x12)"),
                "Back" to listOf("Weighted Pull-ups (3x8)", "Deadlifts (4x6)", "T-Bar Rows (3x10)"),
                "Legs" to listOf("Barbell Squats (4x8)", "Lunges (3x12)", "Romanian Deadlift (3x10)"),
                "Shoulders" to listOf("Military Press (4x8)", "Cable Lateral Raise (3x12)", "Shrugs (3x15)"),
                "Biceps" to listOf("Preacher Curl (3x10)", "Cable Curl (3x12)", "Spider Curl (3x10)"),
                "Triceps" to listOf("Close Grip Bench Press (3x8)", "Overhead Cable Extension (3x12)", "Weighted Dips (3x8)"),
                "Abs" to listOf("Hanging Leg Raise (4x12)", "Ab Wheel Rollout (3x10)", "Plank (3x45 sec)")
            )
        )

        val workouts = workoutMap[level]?.get(muscleGroup) ?: listOf("No workout found")
        return workouts.joinToString(separator = "\n")
    }
}
