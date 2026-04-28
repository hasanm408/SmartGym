package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgym.model.User

class UserDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val etName = findViewById<EditText>(R.id.etUserName)
        val etAge = findViewById<EditText>(R.id.etUserAge)
        val etEmail = findViewById<EditText>(R.id.etUserEmail)
        val etPwd = findViewById<EditText>(R.id.etUserPassword)
        val spGender = findViewById<Spinner>(R.id.spinnerGender)
        val btnContinue = findViewById<Button>(R.id.btnUserContinue)

        spGender.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOf("Male", "Female", "Other"))

        findViewById<Button>(R.id.btnUserPrev).setOnClickListener { finish() }

        btnContinue.setOnClickListener {
            val name = etName.text.toString().trim()
            val age = etAge.text.toString().toIntOrNull() ?: 0
            val email = etEmail.text.toString().trim()
            val pwd = etPwd.text.toString().trim()
            val gender = spGender.selectedItem.toString()

            if (name.isEmpty() || email.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newUser = User(name, email, pwd, age, gender)
            AppData.users.add(newUser)
            AppData.currentUser = newUser

            startActivity(Intent(this, TrainerListActivity::class.java))
        }
    }
}
