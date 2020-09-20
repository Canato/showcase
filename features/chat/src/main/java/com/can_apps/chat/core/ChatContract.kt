package com.can_apps.chat.core

import com.can_apps.chat.bresenter.ChatMessageModel

interface ChatContract {

    interface View {

        fun setupMessages(messages: List<ChatMessageModel>)

        fun addMessage(message: ChatMessageModel)
    }

    interface Presenter {

        fun bind(view: View)

        fun unbind()

        fun onViewCreated()

        fun onSendMessage(message: ChatMessageModel.My)
    }
}