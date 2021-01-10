package com.can_apps.chat.data

import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.core.ChatDomain
import com.can_apps.chat.core.ChatNewDomain
import com.can_apps.message_data_source.MessageDatabaseDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ChatRepository @Inject constructor(
    private val messageDataSource: MessageDatabaseDataSource,
    private val mapper: ChatDtoMapper
) : ChatContract.Repository {

    override suspend fun addMessage(domain: ChatNewDomain) {
        val dto = mapper.toDto(domain)
        if (dto != null) messageDataSource.add(dto)
    }

    override suspend fun updateAndAddMessage(update: ChatDomain, new: ChatNewDomain) {
        val updateDto = mapper.toDto(update)
        val newDto = mapper.toDto(new)
        if (newDto != null) {
            if (updateDto != null) messageDataSource.update(updateDto, newDto)
            else messageDataSource.add(newDto)
        }
    }

    override suspend fun getMessages(): List<ChatDomain> =
        messageDataSource.getAll().map { mapper.toDomain(it) }

    override suspend fun getLatest(): ChatDomain? =
        messageDataSource.getLatestValue()?.let { mapper.toDomain(it) }

    override fun getLatestFlow(): Flow<ChatDomain> =
        messageDataSource
            .getLatestValueFlow()
            .map { mapper.toDomain(it) }
}
