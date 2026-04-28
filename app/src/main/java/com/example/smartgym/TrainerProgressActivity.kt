package com.example.smartgym

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgym.model.UserProgress

class TrainerProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_progress)

        val layoutProgressList = findViewById<LinearLayout>(R.id.layoutProgressList)
        val progressList = AppData.getTrainerProgressList()

        progressList.forEach { progress ->
            layoutProgressList.addView(createProgressCard(layoutProgressList, progress))
        }
    }

    private fun createProgressCard(parent: LinearLayout, progress: UserProgress) =
        layoutInflater.inflate(R.layout.item_trainer_progress, parent, false).apply {
            findViewById<TextView>(R.id.tvUserName).text = progress.userName
            findViewById<TextView>(R.id.tvWorkoutStatus).text =
                "Workout done: ${progress.completedExercises}"
            findViewById<TextView>(R.id.tvMealStatus).text =
                "Meals done: ${progress.completedMeals}/3"
            findViewById<TextView>(R.id.tvStreak).text = "Streak: ${progress.streak} days 🔥"
        }
}
