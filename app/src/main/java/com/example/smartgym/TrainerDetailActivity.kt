package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TrainerDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_detail)

        val tvName = findViewById<TextView>(R.id.tvDetailName)
        val tvSpecialization = findViewById<TextView>(R.id.tvDetailSpecialization)
        val tvExperience = findViewById<TextView>(R.id.tvDetailExperience)
        val btnBookTrainer = findViewById<Button>(R.id.btnBookTrainer)

        val trainerName = intent.getStringExtra("name") ?: "Unknown Trainer"
        val specialization = intent.getStringExtra("specialization") ?: "N/A"
        val experience = intent.getStringExtra("experience") ?: "N/A"

        tvName.text = trainerName
        tvSpecialization.text = "Specialization: $specialization"
        tvExperience.text = "Experience: $experience"

        btnBookTrainer.setOnClickListener {
            val bookingIntent = Intent(this, BookingConfirmationActivity::class.java)
            bookingIntent.putExtra("trainerName", trainerName)
            startActivity(bookingIntent)
        }
    }
}
