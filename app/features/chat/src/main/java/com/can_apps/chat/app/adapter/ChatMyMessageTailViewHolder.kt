package com.can_apps.chat.app.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.chat.bresenter.ChatMessageModel
import com.can_apps.chat.databinding.ItemMyMessageTailBinding

internal class ChatMyMessageTailViewHolder(
    binding: ItemMyMessageTailBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val messageText: TextView = binding.itemMessageText

    fun bind(chatMessageModel: ChatMessageModel) {

        messageText.apply {
            visibility = View.VISIBLE
            alpha = 0f
            animate().alpha(1f).setDuration(500L).setListener(null)
        }

        messageText.text = chatMessageModel.text.value
    }
}
