package com.can_apps.chat.app.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.chat.bresenter.ChatMessageModel
import com.can_apps.chat.databinding.ItemOtherMessageTailBinding

internal class ChatOtherMessageTailViewHolder (
    private val binding: ItemOtherMessageTailBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(chatMessageModel: ChatMessageModel) {

        binding.itemMessageText.apply {
            visibility = View.VISIBLE
            alpha = 0f
            animate().alpha(1f).setDuration(500L).setListener(null)
            text = chatMessageModel.text.value
        }
    }
}