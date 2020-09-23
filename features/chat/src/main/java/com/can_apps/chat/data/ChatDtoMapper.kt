package com.can_apps.chat.data

import com.can_apps.chat.core.ChatDomain
import com.can_apps.chat.core.ChatMessageIdDomain
import com.can_apps.chat.core.ChatMessageHolderEnumDto
import com.can_apps.chat.core.ChatMessageTextDomain
import com.can_apps.chat.core.ChatMessageTimestampDomain
import com.can_apps.chat.core.ChatNewDomain
import com.can_apps.message_data_source.MessageDto
import com.can_apps.message_data_source.MessageHolderEnumDto
import com.can_apps.message_data_source.MessageIdDto
import com.can_apps.message_data_source.MessageTextDto
import com.can_apps.message_data_source.MessageTimestampDto
import com.can_apps.message_data_source.NewMessageDto

internal interface ChatDtoMapper {

    fun toDto(domain: ChatNewDomain): NewMessageDto

    fun toDomain(dto: MessageDto): ChatDomain
}

internal class ChatDtoMapperDefault : ChatDtoMapper {

    override fun toDto(domain: ChatNewDomain): NewMessageDto =
        NewMessageDto(
            MessageTextDto(domain.text.value),
            when (domain.holder) {
                ChatMessageHolderEnumDto.MY -> MessageHolderEnumDto.MY
                ChatMessageHolderEnumDto.OTHER -> MessageHolderEnumDto.OTHER
            }
        )

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
