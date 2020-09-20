package com.can_apps.chat.app

import androidx.fragment.app.Fragment
import com.can_apps.chat.R
import com.can_apps.chat.bresenter.ChatMessageModel
import com.can_apps.chat.core.ChatContract

class ChatFragment : Fragment(R.layout.fragment_chat), ChatContract.View {

    override fun setupMessages(messages: List<ChatMessageModel>) {
        TODO("Not yet implemented")
    }

    override fun addMessage(message: ChatMessageModel) {
        TODO("Not yet implemented")
    }
}