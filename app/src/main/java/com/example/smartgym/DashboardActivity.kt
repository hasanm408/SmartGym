package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgym.model.*
import java.text.SimpleDateFormat
import java.util.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val user = AppData.currentUser
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val trainer = AppData.selectedTrainer ?: AppData.trainers.find { it.name == user.selectedTrainerName }
        
        // 1. Greeting
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greeting = when (hour) {
            in 0..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            else -> "Good Evening"
        }
        findViewById<TextView>(R.id.tvUserGreeting).text = "$greeting, ${user.name}"

        // 2. Today Info
        findViewById<TextView>(R.id.tvTodayDay).text = AppData.getTodayDay()
        findViewById<TextView>(R.id.tvTodayDate).text = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date())
        findViewById<TextView>(R.id.tvSessionSlot).text = trainer?.time ?: "--:--"

        // 3. Trainer Card
        findViewById<TextView>(R.id.tvDashTrainerName).text = trainer?.name ?: "No Trainer Assigned"
        findViewById<TextView>(R.id.tvDashTrainerTime).text = "Session: ${trainer?.time ?: "N/A"}"
        
        findViewById<Button>(R.id.btnViewTrainerDetails).setOnClickListener {
            if (trainer != null) {
                val intent = Intent(this, TrainerDetailActivity::class.java)
                intent.putExtra("trainerName", trainer.name)
                startActivity(intent)
            }
        }

        // 4. Today's Workout
        val todayWorkout = trainer?.workoutPlan?.get(AppData.getTodayDay())
        if (todayWorkout != null) {
            findViewById<TextView>(R.id.tvWorkoutTitle).text = "Today's Routine"
            findViewById<TextView>(R.id.tvWorkoutExercises).text = todayWorkout.exercises.joinToString(", ") { it.name }
            findViewById<Button>(R.id.btnStartWorkout).visibility = View.VISIBLE
            findViewById<Button>(R.id.btnStartWorkout).setOnClickListener {
                startActivity(Intent(this, WorkoutExperienceActivity::class.java))
            }
        } else {
            findViewById<TextView>(R.id.tvWorkoutTitle).text = "Rest Day"
            findViewById<TextView>(R.id.tvWorkoutExercises).text = "Recovery is part of progress!"
            findViewById<Button>(R.id.btnStartWorkout).visibility = View.GONE
        }

        // 5. Today's Diet
        val todayDiet = trainer?.dietPlan?.get(AppData.getTodayDay())
        if (todayDiet != null) {
            findViewById<TextView>(R.id.tvMealsSummary).text = "Breakfast: ${todayDiet.breakfast}\nLunch: ${todayDiet.lunch}\nDinner: ${todayDiet.dinner}"
        } else {
            findViewById<TextView>(R.id.tvMealsSummary).text = "No diet plan assigned for today."
        }

        findViewById<View>(R.id.btnOpenDiet).setOnClickListener {
            startActivity(Intent(this, DietExperienceActivity::class.java))
        }

        findViewById<Button>(R.id.btnAskCoach)?.setOnClickListener {
            if (trainer != null) {
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("receiverName", trainer.name)
                startActivity(intent)
            }
        }


        // 6. Progress
        findViewById<TextView>(R.id.tvStreakCount).text = AppData.getStreak(user.name).toString()
        findViewById<TextView>(R.id.tvConsistencyPercent).text = "${AppData.getUserConsistency(user.name)}%"
        
        findViewById<Button>(R.id.btnLogoutUser)?.setOnClickListener {
            AppData.currentUser = null
            startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }
    }
}
