package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class TrainerRegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_registration)

        val etName = findViewById<EditText>(R.id.etTrainerName)
        val etAge = findViewById<EditText>(R.id.etTrainerAge)
        val etExp = findViewById<EditText>(R.id.etTrainerExperience)
        val etEmail = findViewById<EditText>(R.id.etTrainerEmail)
        val etPwd = findViewById<EditText>(R.id.etTrainerPassword)
        val btnNext = findViewById<Button>(R.id.btnTrainerNext)

        btnNext.setOnClickListener {
            val name = etName.text.toString().trim()
            val ageStr = etAge.text.toString().trim()
            val exp = etExp.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pwd = etPwd.text.toString().trim()

            if (name.isEmpty() || ageStr.isEmpty() || exp.isEmpty() || email.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, TrainerAvailabilityActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("age", ageStr.toIntOrNull() ?: 0)
            intent.putExtra("experience", exp)
            intent.putExtra("email", email)
            intent.putExtra("password", pwd)
            startActivity(intent)
        }
        
        findViewById<View>(R.id.btnTrainerPrev)?.setOnClickListener { finish() }
    }
}
