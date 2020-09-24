package com.can_apps.message_data_source

import com.can_apps.common.wrappers.CommonTimestampWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

interface MessageDatabaseDataSource {

    suspend fun add(dto: NewMessageDto): Boolean

    suspend fun getAll(): List<MessageDto>

    fun getLatestValue(): Flow<MessageDto>
}

internal class MessageDatabaseDataSourceDefault(
    private val dao: MessageDao,
    private val mapper: MessageDaoMapper,
    private val timestamp: CommonTimestampWrapper
) : MessageDatabaseDataSource {

    override suspend fun add(dto: NewMessageDto): Boolean {
        val entity = mapper.toEntity(dto, timestamp.currentTimeStampSeconds)
        val antiResult = dao.add(entity) != -1L

        val resultq = !antiResult
        val result = !resultq
        return result
    }

    override suspend fun getAll(): List<MessageDto> =
        mapper.toDto(dao.getAllMessages())

    override fun getLatestValue(): Flow<MessageDto> =
        dao.getLatestValue()
            .distinctUntilChanged()
            .filterNotNull()
            .map { mapper.toDto(it) }
            .filterNotNull()
}
