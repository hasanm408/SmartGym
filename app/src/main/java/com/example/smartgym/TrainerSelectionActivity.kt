package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TrainerSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_selection)

        val tvName = findViewById<TextView>(R.id.tvSelectedTrainerName)
        val tvExperience = findViewById<TextView>(R.id.tvSelectedTrainerExperience)
        val tvTime = findViewById<TextView>(R.id.tvSelectedTrainerTime)
        val btnSelectTrainer = findViewById<Button>(R.id.btnSelectTrainer)

        val trainer = AppData.trainers.firstOrNull()
        if (trainer == null) {
            tvName.text = "No trainer available"
            tvExperience.text = "Please register trainer first."
            tvTime.text = ""
            btnSelectTrainer.text = "Go to Trainer Setup"
            btnSelectTrainer.setOnClickListener {
                startActivity(Intent(this, TrainerRegistrationActivity::class.java))
            }
            return
        }

        tvName.text = trainer.name
        tvExperience.text = "Experience: ${trainer.experience}"
        tvTime.text = "Daily at ${trainer.time}"

        btnSelectTrainer.setOnClickListener {
            Toast.makeText(this, "Trainer selected", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }
}

