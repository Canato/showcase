package com.can_apps.chat.bresenter

import com.can_apps.chat.core.ChatDomain
import com.can_apps.chat.core.ChatMessageHolderEnumDto
import com.can_apps.chat.core.ChatMessageTextDomain
import com.can_apps.chat.core.ChatMessageTimestampDomain

internal interface ChatModelMapper {

    fun toDomain(message: ChatMessageModel): ChatDomain

    fun toModel(messages: List<ChatDomain>): List<ChatMessageModel>

    fun toModel(messages: ChatDomain): ChatMessageModel
}

internal class ChatModelMapperDefault : ChatModelMapper {

    override fun toDomain(message: ChatMessageModel): ChatDomain {
        val text = ChatMessageTextDomain(message.text.value)
        val timestamp = ChatMessageTimestampDomain(message.timestamp.value)
        val holder = when (message) {
            is ChatMessageModel.My -> ChatMessageHolderEnumDto.MY
            is ChatMessageModel.Other -> ChatMessageHolderEnumDto.OTHER
        }

        return ChatDomain(text, timestamp, holder)
    }

    override fun toModel(messages: List<ChatDomain>): List<ChatMessageModel> =
        messages.map { toModel(it) }

    override fun toModel(messages: ChatDomain): ChatMessageModel {
        val text = ChatMessageTextModel(messages.text.value)
        val timestamp = ChatMessageTimestampModel(messages.timestamp.value)
        return when (messages.holder) {
            ChatMessageHolderEnumDto.MY -> ChatMessageModel.My(text, timestamp)
            ChatMessageHolderEnumDto.OTHER -> ChatMessageModel.Other(text, timestamp)
        }
    }
}