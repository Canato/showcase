package com.can_apps.chat.app.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.chat.bresenter.ChatMessageModel
import kotlinx.android.synthetic.main.item_my_message.view.itemMessageText

internal class ChatMessageViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    private val messageText: TextView = root.itemMessageText

    fun bind(chatMessageModel: ChatMessageModel) {

        messageText.apply {
            visibility = View.VISIBLE
            alpha = 0f
            animate().alpha(1f).setDuration(500L).setListener(null)
        }

        messageText.text = chatMessageModel.text.value
    }
}
