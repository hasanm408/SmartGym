package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etLoginEmail)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvGoToRegister)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check Users
            val user = AppData.users.find { it.email == email && it.password == password }
            if (user != null) {
                AppData.currentUser = user
                val intent = if (user.selectedTrainerName == null) {
                    Intent(this, TrainerListActivity::class.java)
                } else {
                    AppData.selectedTrainer = AppData.trainers.find { it.name == user.selectedTrainerName }
                    Intent(this, DashboardActivity::class.java)
                }
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                finish()
                return@setOnClickListener
            }

            // Check Trainers
            val trainer = AppData.trainers.find { it.email == email && it.password == password }
            if (trainer != null) {
                AppData.currentTrainer = trainer
                val intent = Intent(this, TrainerDashboardActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                finish()
                return@setOnClickListener
            }

            Toast.makeText(this, "Account not found. Please register.", Toast.LENGTH_SHORT).show()
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
