package com.can_apps.chat.bresenter

sealed class ChatMessageModel {

    abstract val text: ChatMessageTextModel
    abstract val timestamp: ChatMessageTimestampModel

    data class My(
        override val text: ChatMessageTextModel,
        override val timestamp: ChatMessageTimestampModel
    ) : ChatMessageModel()

    data class Other(
        override val text: ChatMessageTextModel,
        override val timestamp: ChatMessageTimestampModel
    ) : ChatMessageModel()
}

inline class ChatMessageTextModel(val value: String)
inline class ChatMessageTimestampModel(val value: Long)
