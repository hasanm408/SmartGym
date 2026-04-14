package com.example.smartgym.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.R
import com.example.smartgym.model.Trainer

class TrainerAdapter(
    private val trainerList: List<Trainer>,
    private val onTrainerClick: (Trainer) -> Unit
) : RecyclerView.Adapter<TrainerAdapter.TrainerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trainer, parent, false)
        return TrainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainerViewHolder, position: Int) {
        val trainer = trainerList[position]
        holder.bind(trainer)
    }

    override fun getItemCount(): Int = trainerList.size

    inner class TrainerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvTrainerName)
        private val tvSpecialization: TextView = itemView.findViewById(R.id.tvTrainerSpecialization)
        private val tvExperience: TextView = itemView.findViewById(R.id.tvTrainerExperience)

        fun bind(trainer: Trainer) {
            tvName.text = trainer.name
            tvSpecialization.text = "Specialization: ${trainer.specialization}"
            tvExperience.text = "Experience: ${trainer.experience}"

            itemView.setOnClickListener {
                onTrainerClick(trainer)
            }
        }
    }
}
