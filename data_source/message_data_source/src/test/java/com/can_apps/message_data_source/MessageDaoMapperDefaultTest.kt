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
        val dto = MessageDto(
            MessageTextDto(text),
            MessageTimestampDto(timeStamp),
            MessageHolderEnumDto.MY
        )
        val expect = MessageEntity(0, text, timeStamp, "my")

        // WHEN
        val result = mapper.toEntity(dto)

        // THEN
        assertEquals(expect, result)
    }

    @Test
    fun `GIVEN entity, WHEN map, THEN dto`() {
        // GIVEN
        val text = "C.H.O.O."
        val timeStamp = 66L
        val entityList = listOf(MessageEntity(0, text, timeStamp, "other"))
        val expect = listOf(
            MessageDto(
                MessageTextDto(text),
                MessageTimestampDto(timeStamp),
                MessageHolderEnumDto.OTHER
            )
        )

        // WHEN
        val result = mapper.toDto(entityList)

        // THEN
        assertEquals(expect, result)
    }
}
