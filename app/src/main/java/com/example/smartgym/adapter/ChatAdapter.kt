package com.example.smartgym.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.R
import com.example.smartgym.model.Message

class ChatAdapter(
    private val messages: List<Message>,
    private val currentUserName: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_SENT = 1
        private const val TYPE_RECEIVED = 2
    }

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvContent = view.findViewById<TextView>(R.id.tvMessageContent)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].senderName == currentUserName) TYPE_SENT else TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = if (viewType == TYPE_SENT) R.layout.item_message_sent else R.layout.item_message_received
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        (holder as MessageViewHolder).tvContent.text = message.content
    }

    override fun getItemCount() = messages.size
}
