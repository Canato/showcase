package com.can_apps.chat.data

import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.core.ChatDomain
import com.can_apps.chat.core.ChatNewDomain
import com.can_apps.message_data_source.MessageDatabaseDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ChatRepository(
    private val messageDataSource: MessageDatabaseDataSource,
    private val mapper: ChatDtoMapper
) : ChatContract.Repository {

    override suspend fun addMessage(domain: ChatNewDomain) {
        val dto = mapper.toDto(domain)
        messageDataSource.add(dto)
    }

    override suspend fun getMessages(): List<ChatDomain> =
        messageDataSource.getAll().map { mapper.toDomain(it) }

    override fun getLatest(): Flow<ChatDomain> =
        messageDataSource
            .getLatestValue()
            .map { mapper.toDomain(it) }
}
