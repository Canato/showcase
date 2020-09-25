package com.can_apps.message_data_source

internal interface MessageDaoMapper {

    fun toEntity(dto: NewMessageDto, timestamp: Long): MessageEntity
    fun toDto(messages: List<MessageEntity>): List<MessageDto>
    fun toDto(messages: MessageEntity): MessageDto?
}

internal class MessageDaoMapperDefault : MessageDaoMapper {

    override fun toEntity(dto: NewMessageDto, timestamp: Long): MessageEntity =
        MessageEntity(0, dto.text.value, timestamp, dto.holder.value, 1)
// if (dto.isReadBadge.value) 1 else 0
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
