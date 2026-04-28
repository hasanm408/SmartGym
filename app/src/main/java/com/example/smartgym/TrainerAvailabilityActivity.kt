package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class TrainerAvailabilityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_availability)

        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupDays)
        val btnNext = findViewById<Button>(R.id.btnAvailNext)
        val btnPrev = findViewById<Button>(R.id.btnAvailPrev)

        btnNext.setOnClickListener {
            val selectedDays = mutableListOf<String>()
            for (i in 0 until chipGroup.childCount) {
                val chip = chipGroup.getChildAt(i) as Chip
                if (chip.isChecked) {
                    selectedDays.add(chip.text.toString())
                }
            }

            if (selectedDays.isEmpty()) {
                Toast.makeText(this, "Select at least one day", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, TrainerTimeSlotActivity::class.java)
            intent.putExtras(this.intent.extras!!)
            intent.putStringArrayListExtra("availableDays", ArrayList(selectedDays))
            startActivity(intent)
        }

        btnPrev.setOnClickListener { finish() }
    }
}
