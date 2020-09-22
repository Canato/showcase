package com.can_apps.message_data_source

internal interface MessageDaoMapper {

    fun toEntity(dto: MessageDto): MessageEntity
    fun toDto(messages: List<MessageEntity>): List<MessageDto>
    fun toDto(messages: MessageEntity): MessageDto?
}

internal class MessageDaoMapperDefault : MessageDaoMapper {

    override fun toEntity(dto: MessageDto): MessageEntity =
        MessageEntity(
            0,
            dto.text.value,
            dto.timestamp.value,
            dto.holder.value
        )

    override fun toDto(messages: List<MessageEntity>): List<MessageDto> =
        messages.mapNotNull {
            when (val holder = MessageHolderEnumDto.fromString(it.holder)) {
                null -> null
                else -> MessageDto(
                    MessageTextDto(it.text),
                    MessageTimestampDto(it.timestamp),
                    holder
                )
            }
        }

    override fun toDto(messages: MessageEntity): MessageDto? =
        when (val holder = MessageHolderEnumDto.fromString(messages.holder)) {
            null -> null
            else -> MessageDto(
                MessageTextDto(messages.text),
                MessageTimestampDto(messages.timestamp),
                holder
            )
        }
}