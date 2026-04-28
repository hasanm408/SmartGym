package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgym.model.*
import java.io.Serializable

class DietPlanActivity : AppCompatActivity() {
    private val dietRowsByDay = mutableMapOf<String, DietInputRow>()

    data class DietInputRow(
        val etBreakfast: EditText,
        val etLunch: EditText,
        val etDinner: EditText,
        val etSnacks: EditText
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_plan)

        val layoutDietSections = findViewById<LinearLayout>(R.id.layoutDietSections)
        val availableDays = intent.getStringArrayListExtra("availableDays") ?: emptyList<String>()

        availableDays.forEach { day ->
            addDietSection(layoutDietSections, day)
        }

        findViewById<Button>(R.id.btnDietPrev).setOnClickListener { finish() }

        findViewById<Button>(R.id.btnFinishRegistration).setOnClickListener {
            val dietPlan = mutableMapOf<String, DayDiet>()
            availableDays.forEach { day ->
                val row = dietRowsByDay[day] ?: return@forEach
                val b = row.etBreakfast.text.toString().trim()
                val l = row.etLunch.text.toString().trim()
                val d = row.etDinner.text.toString().trim()
                val s = row.etSnacks.text.toString().trim()
                
                if (b.isNotEmpty() || l.isNotEmpty() || d.isNotEmpty()) {
                    dietPlan[day] = DayDiet(b, l, d, s)
                }
            }

            if (dietPlan.isEmpty()) {
                Toast.makeText(this, "Add at least one meal plan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Move to Review Activity
            val nextIntent = Intent(this, TrainerReviewActivity::class.java)
            nextIntent.putExtras(intent.extras!!)
            nextIntent.putExtra("dietPlan", dietPlan as Serializable)
            startActivity(nextIntent)
        }
    }

    private fun addDietSection(parent: LinearLayout, day: String) {
        val rowView = layoutInflater.inflate(R.layout.item_diet_input, parent, false)
        rowView.findViewById<TextView>(R.id.tvDietDay).text = day
        val b = rowView.findViewById<EditText>(R.id.etBreakfast)
        val l = rowView.findViewById<EditText>(R.id.etLunch)
        val d = rowView.findViewById<EditText>(R.id.etDinner)
        val s = rowView.findViewById<EditText>(R.id.etSnacks)

        dietRowsByDay[day] = DietInputRow(b, l, d, s)
        parent.addView(rowView)
    }
}
