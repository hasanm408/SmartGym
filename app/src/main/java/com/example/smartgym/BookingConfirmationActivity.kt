package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BookingConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_confirmation)

        val tvBookingMessage = findViewById<TextView>(R.id.tvBookingMessage)
        val btnBackToDashboard = findViewById<Button>(R.id.btnBackToDashboard)

        val trainerName = intent.getStringExtra("trainerName") ?: "Trainer"
        tvBookingMessage.text = "Booking confirmed with $trainerName!"

        btnBackToDashboard.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
