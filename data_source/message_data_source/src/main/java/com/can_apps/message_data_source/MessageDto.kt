package com.can_apps.message_data_source

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

inline class MessageIdDto(val value: Long)
inline class MessageTextDto(val value: String)
inline class MessageTimestampDto(val value: Long)
inline class MessageTailDto(val value: Boolean)
enum class MessageHolderEnumDto(val value: String) {
    MY("my"),
    OTHER("other");

    companion object {

        fun fromString(string: String): MessageHolderEnumDto? =
            values().find { string.toLowerCase(Locale.getDefault()) == it.value }
    }
}
