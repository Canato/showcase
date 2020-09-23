package com.can_apps.chat.core

import io.mockk.every
import io.mockk.mockk
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
        val expected = ChatMessageTextDomain("$text?")
        val domain = mockk<ChatDomain>(relaxed = true)

        every { domain.text.value } returns text

        // WHEN
        val result = interactor.getSystemAnswer(domain)

        // THEN
        assertEquals(expected, result.text)
    }

    @Test
    fun `GIVEN empty, WHEN get answer, THEN return question mark`() {
        // GIVEN
        val text = ""
        val expected = ChatMessageTextDomain("?")
        val domain = mockk<ChatDomain>(relaxed = true)

        every { domain.text.value } returns text

        // WHEN
        val result = interactor.getSystemAnswer(domain)

        // THEN
        assertEquals(expected, result.text)
    }

    @Test
    fun `GIVEN question, WHEN get answer, THEN return double question mark`() {
        // GIVEN
        val text = "When?"
        val expected = ChatMessageTextDomain("$text?")
        val domain = mockk<ChatDomain>(relaxed = true)

        every { domain.text.value } returns text

        // WHEN
        val result = interactor.getSystemAnswer(domain)

        // THEN
        assertEquals(expected, result.text)
    }
}
