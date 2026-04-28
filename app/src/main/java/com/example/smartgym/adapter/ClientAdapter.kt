package com.example.smartgym.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.AppData
import com.example.smartgym.R
import com.example.smartgym.model.User

class ClientAdapter(private val clients: List<User>) : RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    class ClientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvClientName)
        val tvInfo = view.findViewById<TextView>(R.id.tvClientInfo)
        val tvStatus = view.findViewById<TextView>(R.id.tvClientStatus)
        val btnMessage = view.findViewById<View>(R.id.btnMessageClient)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_client, parent, false)
        return ClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = clients[position]
        val consistency = AppData.getUserConsistency(client.name)
        
        holder.tvName.text = client.name
        holder.tvInfo.text = "Age: ${client.age} | Consistency: $consistency%"
        
        val todayLog = AppData.workoutLogs.find { it.userName == client.name && it.date == AppData.getTodayDateKey() }
        holder.tvStatus.text = if (todayLog?.isCompleted == true) "Completed" else "Pending"
        holder.tvStatus.alpha = if (todayLog?.isCompleted == true) 1.0f else 0.6f

        holder.btnMessage.setOnClickListener {
            val context = holder.itemView.context
            val intent = android.content.Intent(context, com.example.smartgym.ChatActivity::class.java)
            intent.putExtra("receiverName", client.name)
            context.startActivity(intent)
        }
    }


    override fun getItemCount() = clients.size
}
