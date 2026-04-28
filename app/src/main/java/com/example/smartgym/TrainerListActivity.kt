package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.adapter.TrainerAdapter

class TrainerListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_list)

        val rv = findViewById<RecyclerView>(R.id.rvTrainers)
        rv.layoutManager = LinearLayoutManager(this)
        
        val trainers = AppData.trainers
        rv.adapter = TrainerAdapter(trainers) { trainer ->
            val intent = Intent(this, TrainerDetailActivity::class.java)
            intent.putExtra("trainerName", trainer.name)
            startActivity(intent)
        }
    }
}
