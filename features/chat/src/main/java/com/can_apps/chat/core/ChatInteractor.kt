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

    companion object {

        const val TWENTY_MILLIS = 20000
        const val ONE_HOUR_MILLIS = 3600000
    }

    private var latestTimestamp = ChatMessageTimestampDomain(0L)

    override fun getSystemAnswer(domain: ChatNewDomain): ChatNewDomain =
        ChatNewDomain(
            ChatMessageTextDomain("${domain.text.value}?"),
            ChatMessageHolderEnumDomain.OTHER
        )

    override suspend fun addMessage(domain: ChatNewDomain) {
        val previous = repository.getLatest()

        if (previous != null && previous.holder == domain.holder) {
            val twentySecondsGap =
                (time.currentTimeStampMillis - previous.timestamp.value) > TWENTY_MILLIS

            repository.updateAndAddMessage(
                previous.copy(hasTail = ChatMessageTailDomain(twentySecondsGap)),
                domain
            )
        } else repository.addMessage(domain)
    }

    override suspend fun getMessagesFlow(): Flow<ChatDomain> = flow {
        val messages = repository.getMessages()

        addSystemMessages(messages)
    }

    override fun getLatestFlow(): Flow<ChatDomain> =
        repository.getLatestFlow().transform {
            addSystemMessages(listOf(it))
        }

    private suspend fun FlowCollector<ChatDomain>.addSystemMessages(
        messages: List<ChatDomain>
    ) {
        if (messages.isNotEmpty()) {
            var position = messages.lastIndex

            while (position >= 0) {
                val timeDiff = messages[position].timestamp.value - latestTimestamp.value
                if (timeDiff > ONE_HOUR_MILLIS) {
                    latestTimestamp = messages[position].timestamp
                    val systemMsg = ChatDomain(
                        ChatMessageIdDomain(latestTimestamp.value),
                        ChatMessageTextDomain(time.toDate(latestTimestamp.value)),
                        latestTimestamp,
                        ChatMessageHolderEnumDomain.SYSTEM,
                        ChatMessageTailDomain(false)
                    )
                    emit(systemMsg)
                }
                emit(messages[position])
                position--
            }
        }
    }
}
