package com.can_apps.chat.core

internal class ChatInteractor : ChatContract.Interactor {

    override fun getSystemAnswer(message: ChatMessageTextDomain): ChatMessageTextDomain =
        ChatMessageTextDomain("${message.value}?")
}
