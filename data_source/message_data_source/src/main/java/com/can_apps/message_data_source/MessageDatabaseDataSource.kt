package com.can_apps.message_data_source

import android.util.Log
import com.can_apps.common.wrappers.CommonTimestampWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

interface MessageDatabaseDataSource {

    suspend fun add(dto: NewMessageDto): Boolean

    suspend fun update(dto: MessageDto): Boolean

    suspend fun getAll(): List<MessageDto>

    suspend fun getLatestValue(): MessageDto?

    fun getLatestValueFlow(): Flow<MessageDto>
}

internal class MessageDatabaseDataSourceDefault(
    private val dao: MessageDao,
    private val mapper: MessageDaoMapper,
    private val timestamp: CommonTimestampWrapper
) : MessageDatabaseDataSource {

    override suspend fun add(dto: NewMessageDto): Boolean =
        dao.add(mapper.toEntity(dto, timestamp.currentTimeStampMillis)) != -1L

    override suspend fun update(dto: MessageDto): Boolean =
        dao.add(mapper.toEntity(dto)) != -1L

    override suspend fun getAll(): List<MessageDto> =
        mapper.toDto(dao.getAllMessages())

    override suspend fun getLatestValue(): MessageDto? =
        dao.getLatestValue()?.let { mapper.toDto(it) }

    override fun getLatestValueFlow(): Flow<MessageDto> =
        dao.getLatestValueFlow()
            .distinctUntilChanged()
            .filterNotNull()
            .map {
                mapper.toDto(it)
            }
            .filterNotNull()
}
