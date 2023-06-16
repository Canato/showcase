package com.can_apps.chat.bresenter

sealed class ChatMessageModel {

    abstract val id: ChatMessageIdModel
    abstract val text: ChatMessageTextModel

    data class My(
        override val id: ChatMessageIdModel,
        override val text: ChatMessageTextModel
    ) : ChatMessageModel()

    data class MyWithTail(
        override val id: ChatMessageIdModel,
        override val text: ChatMessageTextModel
    ) : ChatMessageModel()

    data class Other(
        override val id: ChatMessageIdModel,
        override val text: ChatMessageTextModel
    ) : ChatMessageModel()

    data class OtherWithTail(
        override val id: ChatMessageIdModel,
        override val text: ChatMessageTextModel
    ) : ChatMessageModel()

    data class System(
        override val id: ChatMessageIdModel,
        override val text: ChatMessageTextModel
    ) : ChatMessageModel()
}

@JvmInline
value class ChatMessageIdModel(val value: Long)
@JvmInline
value class ChatMessageTextModel(val value: String)
