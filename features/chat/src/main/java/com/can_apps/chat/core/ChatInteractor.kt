package com.can_apps.chat.core

import com.can_apps.common.wrappers.CommonTimestampWrapper
import kotlinx.coroutines.flow.Flow

internal class ChatInteractor(
    private val repository: ChatContract.Repository,
    private val timestamp: CommonTimestampWrapper
) : ChatContract.Interactor {

    override fun getSystemAnswer(domain: ChatNewDomain): ChatNewDomain =
        ChatNewDomain(
            ChatMessageTextDomain("${domain.text.value}?"),
            ChatMessageHolderEnumDto.OTHER
        )

    override suspend fun addMessage(domain: ChatNewDomain) {
        repository.addMessage(domain)
    }

    override suspend fun getMessages(): List<ChatDomain> =
        repository.getMessages()

    override fun getLatest(): Flow<ChatDomain> =
        repository.getLatest()
}
