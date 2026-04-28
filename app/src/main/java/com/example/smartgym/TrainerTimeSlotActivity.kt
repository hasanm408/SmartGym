package com.example.smartgym

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class TrainerTimeSlotActivity : AppCompatActivity() {
    private var selectedTime = ""
    private var selectedView: CardView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_time_slot)

        val btnNext = findViewById<Button>(R.id.btnTimeNext)
        val btnPrev = findViewById<Button>(R.id.btnTimePrev)

        val slots = listOf(
            findViewById<View>(R.id.slot1) as CardView to "07:00 AM",
            findViewById<View>(R.id.slot2) as CardView to "09:00 AM",
            findViewById<View>(R.id.slot3) as CardView to "05:00 PM",
            findViewById<View>(R.id.slot4) as CardView to "07:00 PM"
        )

        slots.forEach { (card, time) ->
            card.findViewById<TextView>(R.id.tvTimeValue).text = time
            card.setOnClickListener {
                selectTime(card, time, btnNext)
            }
        }

        btnNext.setOnClickListener {
            val intent = Intent(this, WorkoutPlanActivity::class.java)
            intent.putExtras(this.intent.extras!!)
            intent.putExtra("time", selectedTime)
            startActivity(intent)
        }

        btnPrev.setOnClickListener { finish() }
    }

    private fun selectTime(card: CardView, time: String, btnNext: Button) {
        // Reset previous selection
        selectedView?.setCardBackgroundColor(ContextCompat.getColor(this, R.color.surface))
        selectedView?.findViewById<TextView>(R.id.tvTimeValue)?.setTextColor(ContextCompat.getColor(this, R.color.text_primary))

        // Set new selection
        selectedTime = time
        selectedView = card
        card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        card.findViewById<TextView>(R.id.tvTimeValue).setTextColor(Color.WHITE)

        // Enable next button
        btnNext.isEnabled = true
        btnNext.alpha = 1.0f
    }
}
