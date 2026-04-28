package com.example.smartgym.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.R
import com.example.smartgym.model.Trainer

class TrainerAdapter(
    private val trainers: List<Trainer>,
    private val onItemClick: (Trainer) -> Unit
) : RecyclerView.Adapter<TrainerAdapter.TrainerViewHolder>() {

    class TrainerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvTrainerName)
        val tvInfo = view.findViewById<TextView>(R.id.tvTrainerInfo)
        val tvTime = view.findViewById<TextView>(R.id.tvTrainerTime)
        val btnView = view.findViewById<View>(R.id.btnViewDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trainer, parent, false)
        return TrainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainerViewHolder, position: Int) {
        val trainer = trainers[position]
        holder.tvName.text = trainer.name
        holder.tvInfo.text = "Exp: ${trainer.experience} yrs | Age: ${trainer.age}"
        holder.tvTime.text = "${trainer.time} Daily"
        
        holder.itemView.setOnClickListener { onItemClick(trainer) }
        holder.btnView.setOnClickListener { onItemClick(trainer) }
    }

    override fun getItemCount() = trainers.size
}
