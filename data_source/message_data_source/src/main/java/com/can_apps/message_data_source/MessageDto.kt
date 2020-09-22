package com.can_apps.message_data_source

import java.util.Locale

data class MessageDto(
    val text: MessageTextDto,
    val timestamp: MessageTimestampDto,
    val holder: MessageHolderEnumDto
)

inline class MessageTextDto(val value: String)
inline class MessageTimestampDto(val value: Long)
enum class MessageHolderEnumDto(val value: String) {
    MY("my"),
    OTHER("other");

    companion object {

        fun fromString(string: String): MessageHolderEnumDto? =
            values().find { string.toLowerCase(Locale.getDefault()) == it.value }
    }
}
