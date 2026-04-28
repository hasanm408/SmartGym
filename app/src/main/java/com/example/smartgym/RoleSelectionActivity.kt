package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class RoleSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_selection)

        findViewById<View>(R.id.btnContinueUser).setOnClickListener {
            startActivity(Intent(this, UserDetailsActivity::class.java))
        }

        findViewById<View>(R.id.btnContinueTrainer).setOnClickListener {
            startActivity(Intent(this, TrainerRegistrationActivity::class.java))
        }
    }
}
