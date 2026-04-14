package com.example.smartgym

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.adapter.TrainerAdapter
import com.example.smartgym.model.Trainer

class TrainerListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_list)

        val rvTrainers = findViewById<RecyclerView>(R.id.rvTrainers)

        val trainerList = listOf(
            Trainer("Rahul Sharma", "Strength Training", "5 years"),
            Trainer("Priya Verma", "Weight Loss", "4 years"),
            Trainer("Amit Singh", "Bodybuilding", "7 years"),
            Trainer("Neha Kapoor", "Functional Fitness", "6 years")
        )

        rvTrainers.layoutManager = LinearLayoutManager(this)
        rvTrainers.adapter = TrainerAdapter(trainerList) { trainer ->
            val intent = Intent(this, TrainerDetailActivity::class.java)
            intent.putExtra("name", trainer.name)
            intent.putExtra("specialization", trainer.specialization)
            intent.putExtra("experience", trainer.experience)
            startActivity(intent)
        }
    }
}
