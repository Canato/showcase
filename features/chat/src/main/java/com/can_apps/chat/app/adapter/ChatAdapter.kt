package com.can_apps.chat.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.chat.R
import com.can_apps.chat.bresenter.ChatMessageModel
import javax.inject.Inject

// todo canato
internal class ChatAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val myMsg = R.layout.item_my_message
        val otherMsg = R.layout.item_other_message
        val systemMsg = R.layout.item_system_message
        val myMsgTail = R.layout.item_my_message_tail
        val otherMsgTail = R.layout.item_other_message_tail
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
            is ChatMessageModel.MyWithTail -> myMsgTail
            is ChatMessageModel.OtherWithTail -> otherMsgTail
        }

    fun addToList(message: ChatMessageModel) {
        when {
            items.isEmpty() -> {
                items.add(message)
                notifyDataSetChanged()
            }
            message.id == items[0].id -> {
                items[0] = message
                notifyItemChanged(0)
            }
            else -> {
                items.add(0, message)
                notifyItemInserted(0)
            }
        }
    }
}
