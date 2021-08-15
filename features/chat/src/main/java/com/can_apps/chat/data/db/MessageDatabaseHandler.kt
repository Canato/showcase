package com.can_apps.chat.data.db

import com.can_apps.chat.data.MessageDto
import com.can_apps.chat.data.NewMessageDto
import com.can_apps.common.wrappers.CommonTimestampWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

internal class MessageDatabaseHandler(
    private val dao: MessageDao,
    private val mapper: MessageDaoMapper,
    private val timestamp: CommonTimestampWrapper
) {

    fun add(dto: NewMessageDto): Boolean =
        dao.add(mapper.toEntity(dto, timestamp.currentTimeStampMillis)) != -1L

    suspend fun update(updateDto: MessageDto, newDto: NewMessageDto) {
        dao.add(mapper.toEntity(updateDto))
        delay(10) // This is a hack fix, when ROOM DB update and add item in sequence really fast, it will only trigger data change for the last item.
        dao.add(mapper.toEntity(newDto, timestamp.currentTimeStampMillis))
    }

    fun getAll(): List<MessageDto> =
        mapper.toDto(dao.getAllMessages())

    fun getLatestValue(): MessageDto? =
        dao.getLatestValue()?.let { mapper.toDto(it) }

    fun getLatestValueFlow(): Flow<MessageDto> =
        dao.getLatestValueFlow()
            .filterNotNull()
            .distinctUntilChanged()
            .map { mapper.toDto(it) }
            .filterNotNull()
}