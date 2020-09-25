package com.can_apps.chat.bresenter

sealed class ChatMessageModel {

    abstract val id: ChatMessageIdModel
    abstract val text: ChatMessageTextModel

    data class My(
        override val id: ChatMessageIdModel,
        override val text: ChatMessageTextModel
    ) : ChatMessageModel()

    data class Other(
        override val id: ChatMessageIdModel,
        override val text: ChatMessageTextModel
    ) : ChatMessageModel()

    data class System(
        override val id: ChatMessageIdModel,
        override val text: ChatMessageTextModel
    ) : ChatMessageModel()
}

inline class ChatMessageIdModel(val value: Long)
inline class ChatMessageTextModel(val value: String)
