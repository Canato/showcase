package com.can_apps.chat.data

import java.util.Locale

data class NewMessageDto(
    val text: MessageTextDto,
    val holder: MessageHolderEnumDto
)

data class MessageDto(
    val id: MessageIdDto,
    val text: MessageTextDto,
    val timestamp: MessageTimestampDto,
    val holder: MessageHolderEnumDto,
    val hasTail: MessageTailDto
)

@JvmInline
value class MessageIdDto(val value: Long)
@JvmInline
value class MessageTextDto(val value: String)
@JvmInline
value class MessageTimestampDto(val value: Long)
@JvmInline
value class MessageTailDto(val value: Boolean)
enum class MessageHolderEnumDto(val value: String) {
    MY("my"),
    OTHER("other");

    companion object {

        fun fromString(string: String): MessageHolderEnumDto? =
            values().find { string.lowercase(Locale.getDefault()) == it.value }
    }
}
