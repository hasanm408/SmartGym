package com.example.smartgym

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DietExperienceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_experience)

        val user = AppData.currentUser ?: return
        val trainer = AppData.selectedTrainer ?: AppData.trainers.find { it.name == user.selectedTrainerName } ?: return
        val todayDiet = trainer.dietPlan[AppData.getTodayDay()]

        findViewById<TextView>(R.id.tvDietDaySubtitle).text = "Follow your custom diet for ${AppData.getTodayDay()}"

        val tvB = findViewById<TextView>(R.id.tvBreakfastDesc)
        val tvL = findViewById<TextView>(R.id.tvLunchDesc)
        val tvD = findViewById<TextView>(R.id.tvDinnerDesc)

        if (todayDiet != null) {
            tvB.text = todayDiet.breakfast.ifEmpty { "Not assigned" }
            tvL.text = todayDiet.lunch.ifEmpty { "Not assigned" }
            tvD.text = todayDiet.dinner.ifEmpty { "Not assigned" }
        } else {
            val msg = "No diet plan assigned for today."
            tvB.text = msg; tvL.text = msg; tvD.text = msg
        }

        val log = AppData.getMealLog(user.name, AppData.getTodayDateKey())
        val cbB = findViewById<CheckBox>(R.id.cbBreakfast)
        val cbL = findViewById<CheckBox>(R.id.cbLunch)
        val cbD = findViewById<CheckBox>(R.id.cbDinner)

        cbB.isChecked = log.breakfastDone
        cbL.isChecked = log.lunchDone
        cbD.isChecked = log.dinnerDone

        cbB.setOnCheckedChangeListener { _, isChecked -> log.breakfastDone = isChecked }
        cbL.setOnCheckedChangeListener { _, isChecked -> log.lunchDone = isChecked }
        cbD.setOnCheckedChangeListener { _, isChecked -> log.dinnerDone = isChecked }

        findViewById<Button>(R.id.btnBackToDash).setOnClickListener { finish() }
    }
}
