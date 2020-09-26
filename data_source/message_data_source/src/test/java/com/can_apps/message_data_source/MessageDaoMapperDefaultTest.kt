package com.can_apps.message_data_source

import org.junit.Assert.assertEquals
import org.junit.Test

internal class MessageDaoMapperDefaultTest {

    private val mapper = MessageDaoMapperDefault()

    @Test
    fun `GIVEN dto, WHEN map, THEN entity`() {
        // GIVEN
        val text = "Voltrom"
        val timeStamp = 42L
        val dto = NewMessageDto(MessageTextDto(text), MessageHolderEnumDto.MY)
        val expect = MessageEntity(0, text, timeStamp, "my", 1)

        // WHEN
        val result = mapper.toEntity(dto, timeStamp)

        // THEN
        assertEquals(expect, result)
    }

    @Test
    fun `GIVEN entity, WHEN map, THEN dto`() {
        // GIVEN
        val text = "C.H.O.O."
        val timeStamp = 66L
        val entityList = listOf(MessageEntity(0, text, timeStamp, "other", 0))
        val expect = listOf(
            MessageDto(
                MessageIdDto(0),
                MessageTextDto(text),
                MessageTimestampDto(timeStamp),
                MessageHolderEnumDto.OTHER,
                MessageTailDto(false)
            )
        )

        // WHEN
        val result = mapper.toDto(entityList)

        // THEN
        assertEquals(expect, result)
    }
}
