package com.can_apps.chat.bresenter

import com.can_apps.chat.core.ChatContract

internal class ChatPresenter : ChatContract.Presenter {

    private var view: ChatContract.View? = null

    override fun bind(view: ChatContract.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override fun onViewCreated() {
        view?.setupMessages(emptyList())
    }

    override fun onSendMessage(message: ChatMessageTextModel) {
        if (message.value.isNotBlank()) view?.addMessage(ChatMessageModel.My(message))
    }
}
