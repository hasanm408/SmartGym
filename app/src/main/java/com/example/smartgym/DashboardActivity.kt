package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val btnViewTrainers = findViewById<Button>(R.id.btnViewTrainers)
        val btnWorkoutPlans = findViewById<Button>(R.id.btnWorkoutPlans)
        val btnMyBookings = findViewById<Button>(R.id.btnMyBookings)

        btnViewTrainers.setOnClickListener {
            startActivity(Intent(this, TrainerListActivity::class.java))
        }

        btnWorkoutPlans.setOnClickListener {
            startActivity(Intent(this, WorkoutCategoryActivity::class.java))
        }

        btnMyBookings.setOnClickListener {
            startActivity(Intent(this, MyBookingsActivity::class.java))
        }
    }
}
