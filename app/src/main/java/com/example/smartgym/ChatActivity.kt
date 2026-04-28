package com.example.smartgym

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgym.adapter.ChatAdapter
import com.example.smartgym.model.Message

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val receiverName = intent.getStringExtra("receiverName") ?: ""
        val currentUserName = AppData.currentUser?.name ?: AppData.currentTrainer?.name ?: ""

        findViewById<TextView>(R.id.tvChatWith).text = "Chat with $receiverName"

        val rv = findViewById<RecyclerView>(R.id.rvChatMessages)
        val etMessage = findViewById<EditText>(R.id.etMessage)
        val btnSend = findViewById<ImageButton>(R.id.btnSendMessage)

        val filteredMessages = AppData.messages.filter {
            (it.senderName == currentUserName && it.receiverName == receiverName) ||
            (it.senderName == receiverName && it.receiverName == currentUserName)
        }

        val adapter = ChatAdapter(filteredMessages, currentUserName)
        rv.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        rv.adapter = adapter

        btnSend.setOnClickListener {
            val content = etMessage.text.toString().trim()
            if (content.isNotEmpty()) {
                val msg = Message(currentUserName, receiverName, content)
                AppData.messages.add(msg)
                etMessage.setText("")
                
                // Refresh list
                val newFiltered = AppData.messages.filter {
                    (it.senderName == currentUserName && it.receiverName == receiverName) ||
                    (it.senderName == receiverName && it.receiverName == currentUserName)
                }
                (rv.adapter as ChatAdapter).apply {
                    // This is a simple prototype, so we just re-create or notify
                    // For a real app, we'd use a more efficient update
                    rv.adapter = ChatAdapter(newFiltered, currentUserName)
                    rv.scrollToPosition(newFiltered.size - 1)
                }
            }
        }

        findViewById<ImageButton>(R.id.btnChatBack).setOnClickListener { finish() }
    }
}
