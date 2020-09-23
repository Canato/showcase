package com.can_apps.chat.core

internal class ChatInteractor : ChatContract.Interactor {

    override fun getSystemAnswer(domain: ChatDomain): ChatDomain =
        ChatDomain(
            ChatMessageTextDomain("${domain.text.value}?"),
            domain.timestamp,
            ChatMessageHolderEnumDto.OTHER
        )

}
