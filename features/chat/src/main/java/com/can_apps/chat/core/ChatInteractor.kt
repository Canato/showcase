package com.can_apps.chat.core

internal class ChatInteractor : ChatContract.Interactor {

    override fun getSystemAnswer(domain: ChatDomain): ChatDomain =
        ChatDomain(
            ChatMessageTextDomain("${domain.text.value}?"),
            domain.timestamp,
            ChatMessageHolderEnumDto.OTHER
        )
    override fun getSystemAnswer(message: ChatMessageTextDomain): ChatMessageTextDomain =
        ChatMessageTextDomain("${message.value}?")

    // todo canato
}
