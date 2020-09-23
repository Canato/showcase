package com.can_apps.chat.bresenter

import com.can_apps.chat.core.ChatDomain
import com.can_apps.chat.core.ChatMessageHolderEnumDto
import com.can_apps.chat.core.ChatMessageIdDomain
import com.can_apps.chat.core.ChatMessageTextDomain
import com.can_apps.chat.core.ChatMessageTimestampDomain
import com.can_apps.chat.core.ChatNewDomain

internal interface ChatModelMapper {

    fun toModel(messages: List<ChatDomain>): List<ChatMessageModel>

    fun toModel(messages: ChatDomain): ChatMessageModel

    fun toMyDomain(message: ChatMessageTextModel): ChatNewDomain

    fun toOtherDomain(message: ChatMessageTextModel): ChatNewDomain
}

internal class ChatModelMapperDefault : ChatModelMapper {

    override fun toMyDomain(message: ChatMessageTextModel): ChatNewDomain =
        ChatNewDomain(ChatMessageTextDomain(message.value), ChatMessageHolderEnumDto.MY)

    override fun toOtherDomain(message: ChatMessageTextModel): ChatNewDomain =
        ChatNewDomain(ChatMessageTextDomain(message.value), ChatMessageHolderEnumDto.OTHER)

    override fun toModel(messages: List<ChatDomain>): List<ChatMessageModel> =
        messages.map { toModel(it) }

    override fun toModel(messages: ChatDomain): ChatMessageModel {
        val id = ChatMessageIdModel(messages.id.value)
        val text = ChatMessageTextModel(messages.text.value)
        return when (messages.holder) {
            ChatMessageHolderEnumDto.MY -> ChatMessageModel.My(id, text)
            ChatMessageHolderEnumDto.OTHER -> ChatMessageModel.Other(id, text)
        }
    }
}
