package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ChatAdapter :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private val dataMessage: ArrayList<String> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.textViewMyMessage)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chat_item, viewGroup, false)

        return ViewHolder(view)
    }

    fun updateMessage(newData: ArrayList<String>) {
        dataMessage.addAll(0, newData)
        this.notifyItemRangeInserted(0, newData.size)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.message.text = dataMessage[position]
    }

    override fun getItemCount() = dataMessage.size
}