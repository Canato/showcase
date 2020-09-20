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
        }

    fun updateList(messages: List<ChatMessageModel>) {
        items = messages.toMutableList()
        notifyDataSetChanged()
    }

    fun addToList(message: ChatMessageModel) {
        items.add(message)
        notifyItemInserted(itemCount-1)
    }
}