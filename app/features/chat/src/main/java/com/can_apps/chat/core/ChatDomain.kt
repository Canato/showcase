package com.can_apps.chat.core

import java.util.Locale

internal data class ChatNewDomain(
    val text: ChatMessageTextDomain,
    val holder: ChatMessageHolderEnumDomain
)

internal data class ChatDomain(
    val id: ChatMessageIdDomain,
    val text: ChatMessageTextDomain,
    val timestamp: ChatMessageTimestampDomain,
    val holder: ChatMessageHolderEnumDomain,
    val hasTail: ChatMessageTailDomain
)

@JvmInline
internal value class ChatMessageIdDomain(val value: Long)
@JvmInline
internal value class ChatMessageTextDomain(val value: String)
@JvmInline
internal value class ChatMessageTimestampDomain(val value: Long)
@JvmInline
internal value class ChatMessageTailDomain(val value: Boolean)

internal enum class ChatMessageHolderEnumDomain(val value: String) {
    MY("my"),
    OTHER("other"),
    SYSTEM("system");

    companion object {

        fun fromString(string: String): ChatMessageHolderEnumDomain? =
            values().find { string.lowercase(Locale.getDefault()) == it.value }
    }
}
