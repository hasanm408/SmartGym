package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.smartgym.model.*
import java.io.Serializable

class WorkoutPlanActivity : AppCompatActivity() {
    private val exerciseMap = mapOf(
        "Chest" to listOf("Bench Press", "Incline Press", "Push-ups", "Cable Fly"),
        "Back" to listOf("Deadlift", "Lat Pulldown", "Seated Row", "Pull-Ups"),
        "Legs" to listOf("Squats", "Lunges", "Leg Press", "Hamstring Curl"),
        "Shoulders" to listOf("Shoulder Press", "Lateral Raise", "Front Raise"),
        "Biceps" to listOf("Barbell Curl", "Hammer Curl", "Preacher Curl"),
        "Triceps" to listOf("Pushdown", "Skull Crushers", "Dips"),
        "Abs" to listOf("Crunches", "Plank", "Leg Raises", "Russian Twist")
    )

    private val exerciseRowsByDay = mutableMapOf<String, MutableList<ExerciseInputRow>>()

    data class ExerciseInputRow(
        val spinnerMuscleGroup: Spinner,
        val spinnerExercise: Spinner,
        val etSets: EditText,
        val etReps: EditText
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_plan)

        val layoutDaySections = findViewById<LinearLayout>(R.id.layoutDaySections)
        val availableDays = intent.getStringArrayListExtra("availableDays") ?: emptyList<String>()

        availableDays.forEach { day ->
            addDaySection(layoutDaySections, day)
        }

        findViewById<Button>(R.id.btnWorkoutPrev).setOnClickListener { finish() }

        findViewById<Button>(R.id.btnNextToDiet).setOnClickListener {
            val workoutPlan = mutableMapOf<String, DayWorkout>()
            
            availableDays.forEach { day ->
                val rows = exerciseRowsByDay[day] ?: return@forEach
                val exercises = rows.mapNotNull { row ->
                    val name = row.spinnerExercise.selectedItem?.toString() ?: ""
                    val sets = row.etSets.text.toString().toIntOrNull() ?: 0
                    val reps = row.etReps.text.toString().toIntOrNull() ?: 0
                    if (name.isNotEmpty() && sets > 0 && reps > 0) Exercise(name, sets, reps) else null
                }
                if (exercises.isNotEmpty()) {
                    workoutPlan[day] = DayWorkout(exercises)
                }
            }

            if (workoutPlan.isEmpty()) {
                Toast.makeText(this, "Add at least one exercise", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Move to Diet Plan Activity
            val nextIntent = Intent(this, DietPlanActivity::class.java)
            nextIntent.putExtras(intent.extras!!)
            nextIntent.putExtra("workoutPlan", workoutPlan as Serializable)
            startActivity(nextIntent)
        }
    }

    private fun addDaySection(parent: LinearLayout, day: String) {
        val sectionView = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            setPadding(0, 0, 0, 24)
        }

        val title = TextView(this).apply {
            text = day
            style(R.style.SmartGymSectionHeading)
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }
        
        val exerciseContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val btnAdd = Button(this).apply {
            text = "+ Add Exercise"
            style(R.style.SmartGymSecondaryButton)
        }

        btnAdd.setOnClickListener { addExerciseRow(exerciseContainer, day) }

        sectionView.addView(title)
        sectionView.addView(exerciseContainer)
        sectionView.addView(btnAdd)
        parent.addView(sectionView)

        // Add first row by default
        addExerciseRow(exerciseContainer, day)
    }

    private fun addExerciseRow(container: LinearLayout, day: String) {
        val rowView = layoutInflater.inflate(R.layout.item_exercise_input, container, false)
        val spMuscle = rowView.findViewById<Spinner>(R.id.spinnerMuscleGroup)
        val spExercise = rowView.findViewById<Spinner>(R.id.spinnerExercise)
        val etSets = rowView.findViewById<EditText>(R.id.etSets)
        val etReps = rowView.findViewById<EditText>(R.id.etReps)

        val muscleGroups = exerciseMap.keys.toList()
        spMuscle.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, muscleGroups)

        spMuscle.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: android.view.View?, p2: Int, p3: Long) {
                val exercises = exerciseMap[muscleGroups[p2]] ?: emptyList()
                spExercise.adapter = ArrayAdapter(this@WorkoutPlanActivity, android.R.layout.simple_spinner_dropdown_item, exercises)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        val row = ExerciseInputRow(spMuscle, spExercise, etSets, etReps)
        exerciseRowsByDay.getOrPut(day) { mutableListOf() }.add(row)
        container.addView(rowView)
    }

    private fun View.style(resId: Int) {
        if (this is TextView) {
            this.setTextAppearance(resId)
        }
    }
}
