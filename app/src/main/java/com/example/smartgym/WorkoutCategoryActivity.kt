package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.adapter.SimpleTextAdapter

class WorkoutCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_category)

        val rvLevels = findViewById<RecyclerView>(R.id.rvWorkoutLevels)
        val levels = listOf("Beginner", "Intermediate", "Advanced")

        rvLevels.layoutManager = LinearLayoutManager(this)
        rvLevels.adapter = SimpleTextAdapter(levels) { selectedLevel ->
            val intent = Intent(this, MuscleGroupActivity::class.java)
            intent.putExtra("level", selectedLevel)
            startActivity(intent)
        }
    }
}
