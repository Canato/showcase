package com.can_apps.message_data_source

import com.can_apps.common.wrappers.CommonTimestampWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface MessageDatabaseDataSource {

    suspend fun add(dto: NewMessageDto): Boolean

    suspend fun update(updateDto: MessageDto, newDto: NewMessageDto)

    suspend fun getAll(): List<MessageDto>

    suspend fun getLatestValue(): MessageDto?

    fun getLatestValueFlow(): Flow<MessageDto>
}

internal class MessageDatabaseDataSourceDefault @Inject constructor(
    private val dao: MessageDao,
    private val mapper: MessageDaoMapper,
    private val timestamp: CommonTimestampWrapper
) : MessageDatabaseDataSource {

    override suspend fun add(dto: NewMessageDto): Boolean =
        dao.add(mapper.toEntity(dto, timestamp.currentTimeStampMillis)) != -1L

    override suspend fun update(updateDto: MessageDto, newDto: NewMessageDto) {
        dao.add(mapper.toEntity(updateDto))
        delay(10) // This is a hack fix, when ROOM DB update and add item in sequence really fast, it will only trigger data change for the last item.
        dao.add(mapper.toEntity(newDto, timestamp.currentTimeStampMillis))
    }

    override suspend fun getAll(): List<MessageDto> =
        mapper.toDto(dao.getAllMessages())

    override suspend fun getLatestValue(): MessageDto? =
        dao.getLatestValue()?.let { mapper.toDto(it) }

    override fun getLatestValueFlow(): Flow<MessageDto> =
        dao.getLatestValueFlow()
            .filterNotNull()
            .distinctUntilChanged()
            .map {
                mapper.toDto(it)
            }
            .filterNotNull()
}
