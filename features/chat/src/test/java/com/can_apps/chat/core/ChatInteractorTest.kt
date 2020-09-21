package com.can_apps.chat.core

import org.junit.Assert.assertEquals
import org.junit.Test

internal class ChatInteractorTest {

    private val interactor = ChatInteractor()

    @Test
    fun `GIVEN message, WHEN get answer, THEN return question`() {
        // GIVEN
        val text = "Poesia"
        val message = ChatMessageTextDomain(text)
        val expected = ChatMessageTextDomain("$text?")

        // WHEN
        val result = interactor.getSystemAnswer(message)

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN empty, WHEN get answer, THEN return question mark`() {
        // GIVEN
        val text = ""
        val message = ChatMessageTextDomain(text)
        val expected = ChatMessageTextDomain("?")

        // WHEN
        val result = interactor.getSystemAnswer(message)

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN question, WHEN get answer, THEN return double question mark`() {
        // GIVEN
        val text = "When?"
        val message = ChatMessageTextDomain(text)
        val expected = ChatMessageTextDomain("$text?")

        // WHEN
        val result = interactor.getSystemAnswer(message)

        // THEN
        assertEquals(expected, result)
    }
}
