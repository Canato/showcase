package com.can_apps.chat.core

import com.can_apps.common.wrappers.CommonTimestampWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform

internal class ChatInteractor(
    private val repository: ChatContract.Repository,
    private val time: CommonTimestampWrapper
) : ChatContract.Interactor {

    private var latestTimestamp = ChatMessageTimestampDomain(0L)

    override fun getSystemAnswer(domain: ChatNewDomain): ChatNewDomain =
        ChatNewDomain(
            ChatMessageTextDomain("${domain.text.value}?"),
            ChatMessageHolderEnumDto.OTHER
        )

    override suspend fun addMessage(domain: ChatNewDomain) {
        repository.addMessage(domain)
    }

    override suspend fun getMessages(): Flow<ChatDomain> = flow {
        val messages = repository.getMessages()

        addSystemMessages(messages)
    }

    override fun getLatest(): Flow<ChatDomain> =
        repository.getLatest().transform {
            addSystemMessages(listOf(it))
        }

    private suspend fun FlowCollector<ChatDomain>.addSystemMessages(
        messages: List<ChatDomain>
    ) {
        if (messages.isNotEmpty()) {
            var position = messages.lastIndex

            while (position >= 0) {
                val timeDiff = messages[position].timestamp.value - latestTimestamp.value
                if (timeDiff > 3600) {
                    latestTimestamp = messages[position].timestamp
                    val systemMsg = ChatDomain(
                        ChatMessageIdDomain(latestTimestamp.value),
                        ChatMessageTextDomain(time.toDate(latestTimestamp.value)),
                        latestTimestamp,
                        ChatMessageHolderEnumDto.SYSTEM
                    )
                    emit(systemMsg)
                }
                emit(messages[position])
                position--
            }
        }
    }
}
