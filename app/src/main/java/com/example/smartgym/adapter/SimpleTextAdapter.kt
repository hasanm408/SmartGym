package com.example.smartgym.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.R

class SimpleTextAdapter(
    private val items: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<SimpleTextAdapter.TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_simple_text, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val text = items[position]
        holder.bind(text)
    }

    override fun getItemCount(): Int = items.size

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvItemTitle: TextView = itemView.findViewById(R.id.tvItemTitle)

        fun bind(text: String) {
            tvItemTitle.text = text
            itemView.setOnClickListener { onItemClick(text) }
        }
    }
}
