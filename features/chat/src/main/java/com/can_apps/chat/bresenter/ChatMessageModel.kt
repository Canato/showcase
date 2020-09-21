package com.can_apps.chat.bresenter

sealed class ChatMessageModel {

    abstract val text: ChatMessageTextModel

    data class My(
        override val text: ChatMessageTextModel
    ) : ChatMessageModel()

    data class Other(
        override val text: ChatMessageTextModel
    ) : ChatMessageModel()
}

inline class ChatMessageTextModel(val value: String)
