package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.adapter.ClientAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class TrainerDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_dashboard)

        val trainer = AppData.currentTrainer ?: AppData.trainers.firstOrNull()
        if (trainer == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // Display Info
        findViewById<TextView>(R.id.tvWelcomeTrainer).text = "Welcome, ${trainer.name}"
        findViewById<TextView>(R.id.tvTrainerDashName).text = trainer.name
        findViewById<TextView>(R.id.tvTrainerDashInfo).text = "Age: ${trainer.age} | Exp: ${trainer.experience} Years"
        findViewById<TextView>(R.id.tvTrainerDashTime).text = "Time Slot: ${trainer.time}"

        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupDashDays)
        chipGroup.removeAllViews()
        trainer.availableDays.forEach { day ->
            val chip = Chip(this)
            chip.text = day
            chip.isCheckable = false
            chip.setChipBackgroundColorResource(R.color.primary_light)
            chip.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
            chipGroup.addView(chip)
        }

        findViewById<TextView>(R.id.tvTrainerDashWorkouts).text = if (trainer.workoutPlan.isEmpty()) {
            "No workouts assigned yet."
        } else {
            trainer.workoutPlan.entries.joinToString("\n") { (day, workout) ->
                "• $day: ${workout.exercises.joinToString { it.name }}"
            }
        }

        findViewById<TextView>(R.id.tvTrainerDashDiet).text = if (trainer.dietPlan.isEmpty()) {
            "No diet plans assigned yet."
        } else {
            trainer.dietPlan.entries.joinToString("\n") { (day, diet) ->
                "• $day: Breakfast, Lunch, Dinner"
            }
        }

        // Clients List
        val rvClients = findViewById<RecyclerView>(R.id.rvTrainerClients)
        rvClients.layoutManager = LinearLayoutManager(this)
        
        val myClients = AppData.users.filter { it.selectedTrainerName == trainer.name }
        rvClients.adapter = ClientAdapter(myClients)
        
        findViewById<Button>(R.id.btnLogoutTrainer)?.setOnClickListener {
            AppData.currentTrainer = null
            startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }
    }
}
