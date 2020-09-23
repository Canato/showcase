package com.can_apps.chat.core

import com.can_apps.common.wrappers.CommonTimestampWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class ChatInteractorTest {

    @MockK
    private lateinit var repository: ChatContract.Repository

    @MockK
    private lateinit var timestamp: CommonTimestampWrapper

    @InjectMockKs
    private lateinit var interactor: ChatInteractor

    @Before
    fun setup() = MockKAnnotations.init(this, relaxed = true)

    @Test
    fun `GIVEN message, WHEN get answer, THEN return question`() {
        // GIVEN
        val text = "Poesia"
        val expected = ChatMessageTextDomain("$text?")
        val domain = mockk<ChatNewDomain>(relaxed = true)

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
        val domain = mockk<ChatNewDomain>(relaxed = true)

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
        val domain = mockk<ChatNewDomain>(relaxed = true)

        every { domain.text.value } returns text

        // WHEN
        val result = interactor.getSystemAnswer(domain)

        // THEN
        assertEquals(expected, result.text)
    }
}
