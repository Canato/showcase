package com.can_apps.chat.bresenter

import com.can_apps.chat.core.ChatMessageTextDomain

internal interface ChatModelMapper {

    fun toDomain(message: ChatMessageTextModel): ChatMessageTextDomain

    fun toOtherModel(domain: ChatMessageTextDomain): ChatMessageModel
}

internal class ChatModelMapperDefault : ChatModelMapper {

    override fun toDomain(message: ChatMessageTextModel): ChatMessageTextDomain =
        ChatMessageTextDomain(message.value)

    override fun toOtherModel(domain: ChatMessageTextDomain): ChatMessageModel =
        ChatMessageModel.Other(ChatMessageTextModel(domain.value))
}
