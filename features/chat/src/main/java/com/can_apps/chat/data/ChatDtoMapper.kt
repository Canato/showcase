package com.can_apps.chat.data

import com.can_apps.chat.core.ChatDomain
import com.can_apps.chat.core.ChatMessageHolderEnumDomain
import com.can_apps.chat.core.ChatMessageIdDomain
import com.can_apps.chat.core.ChatMessageTailDomain
import com.can_apps.chat.core.ChatMessageTextDomain
import com.can_apps.chat.core.ChatMessageTimestampDomain
import com.can_apps.chat.core.ChatNewDomain
import com.can_apps.chat.data.db.MessageEntity

internal interface ChatDtoMapper {

    fun toDto(domain: ChatNewDomain): NewMessageDto?

    fun toDto(domain: ChatDomain): MessageDto?

    fun toDomain(dto: MessageDto): ChatDomain
}

internal class ChatDtoMapperDefault : ChatDtoMapper {

    override fun toDto(domain: ChatNewDomain): NewMessageDto? =
        when (domain.holder) {
            ChatMessageHolderEnumDomain.MY -> NewMessageDto(
                MessageTextDto(domain.text.value),
                MessageHolderEnumDto.MY
            )
            ChatMessageHolderEnumDomain.OTHER -> NewMessageDto(
                MessageTextDto(domain.text.value),
                MessageHolderEnumDto.OTHER
            )
            ChatMessageHolderEnumDomain.SYSTEM -> null
        }

    override fun toDto(domain: ChatDomain): MessageDto? {
        val id = MessageIdDto(domain.id.value)
        val text = MessageTextDto(domain.text.value)
        val time = MessageTimestampDto(domain.timestamp.value)
        val hastTail = MessageTailDto(domain.hasTail.value)
        return when (domain.holder) {
            ChatMessageHolderEnumDomain.MY ->
                MessageDto(id, text, time, MessageHolderEnumDto.MY, hastTail)
            ChatMessageHolderEnumDomain.OTHER ->
                MessageDto(id, text, time, MessageHolderEnumDto.OTHER, hastTail)
            ChatMessageHolderEnumDomain.SYSTEM -> null
        }
    }

    override fun toDomain(dto: MessageDto): ChatDomain =
        ChatDomain(
            ChatMessageIdDomain(dto.id.value),
            ChatMessageTextDomain(dto.text.value),
            ChatMessageTimestampDomain(dto.timestamp.value),
            when (dto.holder) {
                MessageHolderEnumDto.MY -> ChatMessageHolderEnumDomain.MY
                MessageHolderEnumDto.OTHER -> ChatMessageHolderEnumDomain.OTHER
            },
            ChatMessageTailDomain(dto.hasTail.value)
        )
}
