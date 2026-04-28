package com.example.smartgym

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.adapter.WorkoutLogAdapter
import com.example.smartgym.model.*

class WorkoutExperienceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_experience)

        val user = AppData.currentUser ?: return
        val trainer = AppData.selectedTrainer ?: AppData.trainers.find { it.name == user.selectedTrainerName } ?: return
        val todayWorkout = trainer.workoutPlan[AppData.getTodayDay()] ?: return

        val log = AppData.getWorkoutLog(user.name, AppData.getTodayDateKey())
        if (log.logs.isEmpty()) {
            todayWorkout.exercises.forEach { ex ->
                log.logs.add(ExerciseLog(exerciseName = ex.name))
            }
        }

        val rv = findViewById<RecyclerView>(R.id.rvWorkoutExperience)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = WorkoutLogAdapter(log.logs, todayWorkout.exercises)

        findViewById<Button>(R.id.btnFinishWorkout).setOnClickListener {
            log.isCompleted = true
            Toast.makeText(this, "Workout Completed! Keep it up!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
