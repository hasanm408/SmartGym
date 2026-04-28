package com.example.smartgym

import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DietDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_detail)

        val tvDietDay = findViewById<TextView>(R.id.tvDietDay)
        val tvBreakfast = findViewById<TextView>(R.id.tvBreakfast)
        val tvLunch = findViewById<TextView>(R.id.tvLunch)
        val tvDinner = findViewById<TextView>(R.id.tvDinner)
        val cbBreakfastDone = findViewById<CheckBox>(R.id.cbBreakfastDone)
        val cbLunchDone = findViewById<CheckBox>(R.id.cbLunchDone)
        val cbDinnerDone = findViewById<CheckBox>(R.id.cbDinnerDone)

        val day = intent.getStringExtra("day")
            ?: SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
        val dateKey = intent.getStringExtra("dateKey")
            ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val userName = AppData.getCurrentUserName()

        val diet = getDietForDay(day)
        val status = AppData.getMealStatus(userName, dateKey)
        tvDietDay.text = day

        tvBreakfast.text = diet?.breakfast ?: "No info"
        tvLunch.text = diet?.lunch ?: "No info"
        tvDinner.text = diet?.dinner ?: "No info"

        cbBreakfastDone.setOnCheckedChangeListener(null)
        cbLunchDone.setOnCheckedChangeListener(null)
        cbDinnerDone.setOnCheckedChangeListener(null)

        cbBreakfastDone.isChecked = status.first
        cbLunchDone.isChecked = status.second
        cbDinnerDone.isChecked = status.third

        val syncMeals: () -> Unit = {
            AppData.setMealStatus(
                userName = userName,
                dateKey = dateKey,
                breakfastDone = cbBreakfastDone.isChecked,
                lunchDone = cbLunchDone.isChecked,
                dinnerDone = cbDinnerDone.isChecked
            )
            val exercises = AppData.selectedTrainer?.workoutPlan?.get(day)?.exercises
                ?: AppData.trainerPlan?.workoutPlan?.get(day)?.exercises
                ?: emptyList()
            val (completed, total) = AppData.getCompletedTaskStats(userName, dateKey, exercises)
            AppData.updateStreak(userName, dateKey, completed, total)
        }
        cbBreakfastDone.setOnCheckedChangeListener { _, _ -> syncMeals() }
        cbLunchDone.setOnCheckedChangeListener { _, _ -> syncMeals() }
        cbDinnerDone.setOnCheckedChangeListener { _, _ -> syncMeals() }

        Toast.makeText(this, "Diet tracking updated", Toast.LENGTH_SHORT).show()
    }

    private fun getDietForDay(day: String): com.example.smartgym.model.DayDiet? {
        return AppData.selectedTrainer?.dietPlan?.get(day)
            ?: AppData.trainerPlan?.dietPlan?.get(day)
    }
}

