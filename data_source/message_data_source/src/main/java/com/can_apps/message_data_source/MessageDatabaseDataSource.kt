package com.can_apps.message_data_source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

interface MessageDatabaseDataSource {

    suspend fun add(dto: MessageDto): Boolean

    fun getAll(): Flow<List<MessageDto>>
}

internal class MessageDatabaseDataSourceDefault(
    private val dao: MessageDao,
    private val mapper: MessageDaoMapper
) : MessageDatabaseDataSource {

    override suspend fun add(dto: MessageDto): Boolean =
        dao.add(mapper.toEntity(dto)) != -1L

    override fun getAll(): Flow<List<MessageDto>> =
        dao.getAllMessages()
            .distinctUntilChanged()
            .map { mapper.toDto(it) }
}