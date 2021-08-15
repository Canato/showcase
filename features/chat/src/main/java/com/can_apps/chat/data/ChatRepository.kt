package com.can_apps.chat.data

import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.core.ChatDomain
import com.can_apps.chat.core.ChatNewDomain
import com.can_apps.chat.data.db.MessageDatabaseHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ChatRepository(
    private val messageDatabaseHandler: MessageDatabaseHandler,
    private val mapper: ChatDtoMapper
) : ChatContract.Repository {

    override suspend fun addMessage(domain: ChatNewDomain) {
        val dto = mapper.toDto(domain)
        if (dto != null) messageDatabaseHandler.add(dto)
    }

    override suspend fun updateAndAddMessage(update: ChatDomain, new: ChatNewDomain) {
        val updateDto = mapper.toDto(update)
        val newDto = mapper.toDto(new)
        if (newDto != null) {
            if (updateDto != null) messageDatabaseHandler.update(updateDto, newDto)
            else messageDatabaseHandler.add(newDto)
        }
    }

    override suspend fun getMessages(): List<ChatDomain> =
        messageDatabaseHandler.getAll().map { mapper.toDomain(it) }

    override suspend fun getLatest(): ChatDomain? =
        messageDatabaseHandler.getLatestValue()?.let { mapper.toDomain(it) }

    override fun getLatestFlow(): Flow<ChatDomain> =
        messageDatabaseHandler
            .getLatestValueFlow()
            .map { mapper.toDomain(it) }
}
