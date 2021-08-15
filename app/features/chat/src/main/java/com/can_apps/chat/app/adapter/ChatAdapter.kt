package com.can_apps.chat.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.chat.bresenter.ChatMessageModel
import com.can_apps.chat.databinding.ItemMyMessageBinding
import com.can_apps.chat.databinding.ItemMyMessageTailBinding
import com.can_apps.chat.databinding.ItemOtherMessageBinding
import com.can_apps.chat.databinding.ItemOtherMessageTailBinding
import com.can_apps.chat.databinding.ItemSystemMessageBinding

internal class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val MY_MSG = 0
        private const val OTHER_MSG = 1
        private const val SYSTEM_MSG = 2
        private const val MY_MSG_TAIL = 3
        private const val OTHER_MSG_TAIL = 4
    }

    private var items = mutableListOf<ChatMessageModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            MY_MSG -> ChatMyMessageViewHolder(
                ItemMyMessageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            OTHER_MSG -> ChatOtherMessageViewHolder(
                ItemOtherMessageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            SYSTEM_MSG -> ChatSystemMessageViewHolder(
                ItemSystemMessageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            MY_MSG_TAIL -> ChatMyMessageTailViewHolder(
                ItemMyMessageTailBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            OTHER_MSG_TAIL -> ChatOtherMessageTailViewHolder(
                ItemOtherMessageTailBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw IllegalArgumentException("Invalid view holder for type $viewType")
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

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is ChatMessageModel.My -> MY_MSG
            is ChatMessageModel.Other -> OTHER_MSG
            is ChatMessageModel.System -> SYSTEM_MSG
            is ChatMessageModel.MyWithTail -> MY_MSG_TAIL
            is ChatMessageModel.OtherWithTail -> OTHER_MSG_TAIL
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
