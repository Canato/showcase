package com.can_apps.chat.bresenter

import com.can_apps.chat.core.ChatDomain
import com.can_apps.chat.core.ChatMessageHolderEnumDomain
import com.can_apps.chat.core.ChatMessageTextDomain
import com.can_apps.chat.core.ChatNewDomain

internal interface ChatModelMapper {

    fun toModel(message: ChatDomain): ChatMessageModel

    fun toMyDomain(message: ChatMessageTextModel): ChatNewDomain

    fun toOtherDomain(message: ChatMessageTextModel): ChatNewDomain
}

internal class ChatModelMapperDefault : ChatModelMapper {

    override fun toMyDomain(message: ChatMessageTextModel): ChatNewDomain =
        ChatNewDomain(ChatMessageTextDomain(message.value), ChatMessageHolderEnumDomain.MY)

    override fun toOtherDomain(message: ChatMessageTextModel): ChatNewDomain =
        ChatNewDomain(ChatMessageTextDomain(message.value), ChatMessageHolderEnumDomain.OTHER)

    override fun toModel(message: ChatDomain): ChatMessageModel {
        val id = ChatMessageIdModel(message.id.value)
        val text = ChatMessageTextModel(message.text.value)
        return when (message.holder) {
            ChatMessageHolderEnumDomain.MY -> ChatMessageModel.My(id, text)
            ChatMessageHolderEnumDomain.OTHER -> ChatMessageModel.Other(id, text)
            ChatMessageHolderEnumDomain.SYSTEM -> ChatMessageModel.System(id, text)
        }
    }
}
