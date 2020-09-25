package com.can_apps.chat.data

import com.can_apps.chat.core.ChatDomain
import com.can_apps.chat.core.ChatMessageHolderEnumDto
import com.can_apps.chat.core.ChatMessageIdDomain
import com.can_apps.chat.core.ChatMessageTextDomain
import com.can_apps.chat.core.ChatMessageTimestampDomain
import com.can_apps.chat.core.ChatNewDomain
import com.can_apps.message_data_source.MessageDto
import com.can_apps.message_data_source.MessageHolderEnumDto
import com.can_apps.message_data_source.MessageTextDto
import com.can_apps.message_data_source.NewMessageDto

internal interface ChatDtoMapper {

    fun toDto(domain: ChatNewDomain): NewMessageDto?

    fun toDomain(dto: MessageDto): ChatDomain
}

internal class ChatDtoMapperDefault : ChatDtoMapper {

    override fun toDto(domain: ChatNewDomain): NewMessageDto? =
        when (domain.holder) {
            ChatMessageHolderEnumDto.MY -> NewMessageDto(
                MessageTextDto(domain.text.value),
                MessageHolderEnumDto.MY
            )
            ChatMessageHolderEnumDto.OTHER -> NewMessageDto(
                MessageTextDto(domain.text.value),
                MessageHolderEnumDto.OTHER
            )
            ChatMessageHolderEnumDto.SYSTEM -> null
        }

    override fun toDomain(dto: MessageDto): ChatDomain =
        ChatDomain(
            ChatMessageIdDomain(dto.id.value),
            ChatMessageTextDomain(dto.text.value),
            ChatMessageTimestampDomain(dto.timestamp.value),
            when (dto.holder) {
                MessageHolderEnumDto.MY -> ChatMessageHolderEnumDto.MY
                MessageHolderEnumDto.OTHER -> ChatMessageHolderEnumDto.OTHER
            }
        )
}
