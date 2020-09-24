package com.can_apps.chat.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.chat.R
import com.can_apps.chat.bresenter.ChatMessageModel

internal class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val myMsg = R.layout.item_my_message
        val otherMsg = R.layout.item_other_message
        val systemMsg = R.layout.item_system_message
    }

    private var items = mutableListOf<ChatMessageModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ChatMessageViewHolder(root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ChatMessageViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is ChatMessageModel.My -> myMsg
            is ChatMessageModel.Other -> otherMsg
            is ChatMessageModel.System -> systemMsg
        }

    fun updateList(messages: List<ChatMessageModel>) {
        items = messages.toMutableList()
        notifyItemRangeInserted(0, itemCount)
    }

    fun addToList(message: ChatMessageModel) {
        if (items.isEmpty()) {
            items.add(0, message)
            notifyItemInserted(0)
        } else if (message.id != items[0].id) {
            items.add(0, message)
            notifyItemInserted(0)
        }
    }
}
