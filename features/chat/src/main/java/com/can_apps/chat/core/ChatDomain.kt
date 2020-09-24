package com.can_apps.chat.core

import java.util.Locale

internal data class ChatNewDomain(
    val text: ChatMessageTextDomain,
    val holder: ChatMessageHolderEnumDto
)

internal data class ChatDomain(
    val id: ChatMessageIdDomain,
    val text: ChatMessageTextDomain,
    val timestamp: ChatMessageTimestampDomain,
    val holder: ChatMessageHolderEnumDto
)

internal inline class ChatMessageIdDomain(val value: Long)
internal inline class ChatMessageTextDomain(val value: String)
internal inline class ChatMessageTimestampDomain(val value: Long)
internal enum class ChatMessageHolderEnumDto(val value: String) {
    MY("my"),
    OTHER("other"),
    SYSTEM("system");

    companion object {

        fun fromString(string: String): ChatMessageHolderEnumDto? =
            values().find { string.toLowerCase(Locale.getDefault()) == it.value }
    }
}
