package com.example.smartgym

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.adapter.BookingAdapter
import com.example.smartgym.model.Booking

class MyBookingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)

        val rvBookings = findViewById<RecyclerView>(R.id.rvBookings)
        val bookings = listOf(
            Booking("Rahul Sharma", "15 Apr 2026", "7:00 AM"),
            Booking("Priya Verma", "16 Apr 2026", "6:30 PM"),
            Booking("Amit Singh", "18 Apr 2026", "8:00 AM")
        )

        rvBookings.layoutManager = LinearLayoutManager(this)
        rvBookings.adapter = BookingAdapter(bookings)
    }
}
