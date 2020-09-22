package com.can_apps.message_data_source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

interface MessageDatabaseDataSource {

    suspend fun add(dto: MessageDto): Boolean

    suspend fun getAll(): List<MessageDto>

    fun getLatestValue(): Flow<MessageDto>
}

internal class MessageDatabaseDataSourceDefault(
    private val dao: MessageDao,
    private val mapper: MessageDaoMapper
) : MessageDatabaseDataSource {

    override suspend fun add(dto: MessageDto): Boolean =
        dao.add(mapper.toEntity(dto)) != -1L

    override suspend fun getAll(): List<MessageDto> =
        mapper.toDto(dao.getAllMessages())

    override fun getLatestValue(): Flow<MessageDto> =
        dao.getLatestValue()
            .distinctUntilChanged()
            .filterNotNull()
            .map { mapper.toDto(it) }
            .filterNotNull()
}
