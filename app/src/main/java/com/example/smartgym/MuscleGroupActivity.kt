package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.adapter.SimpleTextAdapter

class MuscleGroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muscle_group)

        val level = intent.getStringExtra("level") ?: "Beginner"
        val tvSelectedLevel = findViewById<TextView>(R.id.tvSelectedLevel)
        val rvMuscleGroups = findViewById<RecyclerView>(R.id.rvMuscleGroups)

        tvSelectedLevel.text = "Selected Level: $level"

        val muscleGroups = listOf("Chest", "Back", "Legs", "Shoulders", "Biceps", "Triceps", "Abs")

        rvMuscleGroups.layoutManager = LinearLayoutManager(this)
        rvMuscleGroups.adapter = SimpleTextAdapter(muscleGroups) { selectedMuscleGroup ->
            val intent = Intent(this, WorkoutDetailActivity::class.java)
            intent.putExtra("level", level)
            intent.putExtra("muscleGroup", selectedMuscleGroup)
            startActivity(intent)
        }
    }
}
