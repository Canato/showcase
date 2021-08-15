package com.can_apps.chat.data.db

import com.can_apps.chat.data.MessageDto
import com.can_apps.chat.data.MessageHolderEnumDto
import com.can_apps.chat.data.MessageIdDto
import com.can_apps.chat.data.MessageTailDto
import com.can_apps.chat.data.MessageTextDto
import com.can_apps.chat.data.MessageTimestampDto
import com.can_apps.chat.data.NewMessageDto

internal interface MessageDaoMapper {

    fun toEntity(dto: NewMessageDto, timestamp: Long): MessageEntity

    fun toEntity(dto: MessageDto): MessageEntity

    fun toDto(messages: List<MessageEntity>): List<MessageDto>

    fun toDto(messages: MessageEntity): MessageDto?
}

internal class MessageDaoMapperDefault : MessageDaoMapper {

    override fun toEntity(dto: NewMessageDto, timestamp: Long): MessageEntity =
        MessageEntity(0, dto.text.value, timestamp, dto.holder.value, 1)

    override fun toEntity(dto: MessageDto): MessageEntity =
        MessageEntity(
            dto.id.value,
            dto.text.value,
            dto.timestamp.value,
            dto.holder.value,
            if (dto.hasTail.value) 1 else 0
        )

    override fun toDto(messages: List<MessageEntity>): List<MessageDto> =
        messages.mapNotNull { toDto(it) }

    override fun toDto(messages: MessageEntity): MessageDto? =
        when (val holder = MessageHolderEnumDto.fromString(messages.holder)) {
            null -> null
            else -> MessageDto(
                MessageIdDto(messages.id),
                MessageTextDto(messages.text),
                MessageTimestampDto(messages.timestamp),
                holder,
                MessageTailDto(messages.hasTail == 1)
            )
        }
}