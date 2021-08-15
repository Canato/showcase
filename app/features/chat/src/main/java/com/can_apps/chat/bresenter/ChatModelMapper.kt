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

        return if (message.holder == ChatMessageHolderEnumDomain.MY && message.hasTail.value)
            ChatMessageModel.MyWithTail(id, text)
        else if (message.holder == ChatMessageHolderEnumDomain.MY && !message.hasTail.value)
            ChatMessageModel.My(id, text)
        else if (message.holder == ChatMessageHolderEnumDomain.OTHER && message.hasTail.value)
            ChatMessageModel.OtherWithTail(id, text)
        else if (message.holder == ChatMessageHolderEnumDomain.OTHER && !message.hasTail.value)
            ChatMessageModel.Other(id, text)
        else ChatMessageModel.System(id, text)
    }
}
