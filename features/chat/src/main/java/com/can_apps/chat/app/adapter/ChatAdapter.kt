package com.can_apps.chat.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.chat.R
import com.can_apps.chat.bresenter.ChatMessageModel
import com.can_apps.chat.databinding.ItemMyMessageBinding
import com.can_apps.chat.databinding.ItemMyMessageTailBinding
import com.can_apps.chat.databinding.ItemOtherMessageBinding
import com.can_apps.chat.databinding.ItemOtherMessageTailBinding
import com.can_apps.chat.databinding.ItemSystemMessageBinding

internal class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableListOf<ChatMessageModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val root = LayoutInflater.from(parent.context)
        return when (items[viewType]) {
            is ChatMessageModel.My ->
                ChatMyMessageViewHolder(ItemMyMessageBinding.inflate(root, parent, false))
            is ChatMessageModel.Other ->
                ChatOtherMessageViewHolder(ItemOtherMessageBinding.inflate(root, parent, false))
            is ChatMessageModel.System ->
                ChatSystemMessageViewHolder(ItemSystemMessageBinding.inflate(root, parent, false))
            is ChatMessageModel.MyWithTail ->
                ChatMyMessageTailViewHolder(ItemMyMessageTailBinding.inflate(root, parent, false))
            is ChatMessageModel.OtherWithTail -> ChatOtherMessageTailViewHolder(
                ItemOtherMessageTailBinding.inflate(root, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (items[position]) {
            is ChatMessageModel.My ->
                (holder as ChatMyMessageViewHolder).bind(items[position])
            is ChatMessageModel.Other ->
                (holder as ChatOtherMessageViewHolder).bind(items[position])
            is ChatMessageModel.System ->
                (holder as ChatSystemMessageViewHolder).bind(items[position])
            is ChatMessageModel.MyWithTail ->
                (holder as ChatMyMessageTailViewHolder).bind(items[position])
            is ChatMessageModel.OtherWithTail ->
                (holder as ChatOtherMessageTailViewHolder).bind(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = position

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
