package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class TrainerDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_detail)

        val trainerName = intent.getStringExtra("trainerName")
        val trainer = AppData.trainers.find { it.name == trainerName }

        if (trainer == null) {
            Toast.makeText(this, "Trainer not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        findViewById<TextView>(R.id.tvDetailName).text = trainer.name
        findViewById<TextView>(R.id.tvDetailExperience).text = "${trainer.experience} Years Experience"
        findViewById<TextView>(R.id.tvDetailTime).text = "Session Time: ${trainer.time}"

        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupAvailability)
        trainer.availableDays.forEach { day ->
            val chip = Chip(this)
            chip.text = day
            chip.isCheckable = false
            chipGroup.addView(chip)
        }

        findViewById<TextView>(R.id.tvWorkoutPreview).text = trainer.workoutPlan.entries.joinToString("\n") { (day, workout) ->
            "$day: ${workout.exercises.size} exercises"
        }

        findViewById<TextView>(R.id.tvDietPreview).text = trainer.dietPlan.entries.joinToString("\n") { (day, diet) ->
            "$day: Breakfast, Lunch, Dinner"
        }

        findViewById<Button>(R.id.btnConfirmTrainer).setOnClickListener {
            AppData.currentUser?.selectedTrainerName = trainer.name
            AppData.selectedTrainer = trainer
            
            Toast.makeText(this, "Trainer Selected Successfully", Toast.LENGTH_SHORT).show()
            
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
