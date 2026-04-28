package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgym.model.*
import java.io.Serializable

class TrainerReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_review)

        val name = intent.getStringExtra("name") ?: ""
        val age = intent.getIntExtra("age", 0)
        val experience = intent.getStringExtra("experience") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        val time = intent.getStringExtra("time") ?: ""
        val availableDays = intent.getStringArrayListExtra("availableDays") ?: emptyList<String>()
        val workoutPlan = intent.getSerializableExtra("workoutPlan") as? Map<String, DayWorkout> ?: emptyMap()
        val dietPlan = intent.getSerializableExtra("dietPlan") as? Map<String, DayDiet> ?: emptyMap()

        findViewById<TextView>(R.id.tvReviewDetails).text = """
            Name: $name
            Experience: $experience Years
            Time Slot: $time
            Available Days: ${availableDays.joinToString(", ")}
        """.trimIndent()

        findViewById<Button>(R.id.btnFinishSetup).setOnClickListener {
            val newTrainer = Trainer(
                name = name,
                email = email,
                password = password,
                age = age,
                specialization = "General Fitness",
                experience = experience,
                time = time,
                availableDays = availableDays,
                workoutPlan = workoutPlan,
                dietPlan = dietPlan
            )

            AppData.trainers.add(newTrainer)
            AppData.currentTrainer = newTrainer
            
            Toast.makeText(this, "Setup Complete!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, TrainerDashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.btnReviewPrev).setOnClickListener { finish() }
    }
}
