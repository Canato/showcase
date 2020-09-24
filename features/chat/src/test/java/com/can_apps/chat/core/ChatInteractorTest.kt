package com.can_apps.chat.core

import com.can_apps.chat.core.ChatMessageHolderEnumDto.*
import com.can_apps.common.wrappers.CommonTimestampWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class ChatInteractorTest {

    @MockK
    private lateinit var repository: ChatContract.Repository

    @MockK
    private lateinit var timestamp: CommonTimestampWrapper

    private val testDispatcher = TestCoroutineDispatcher()

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

    @Test
    fun `GIVEN messages short period send, WHEN get, THEN add system message on top only`() =
        testDispatcher.runBlockingTest  {
        // GIVEN
        val resultList = mutableListOf<ChatDomain>()
        val time1 = ChatMessageTimestampDomain(80L)
        val time2 = ChatMessageTimestampDomain(75L)
        val time3 = ChatMessageTimestampDomain(70L)
        val time4 = ChatMessageTimestampDomain(70L)
        val time5 = ChatMessageTimestampDomain(60L)
        val msg = ChatDomain(
            ChatMessageIdDomain(42L),
            ChatMessageTextDomain("Oe"),
            ChatMessageTimestampDomain(0L),
            MY
        )
        val messages = listOf(
            msg.copy(timestamp = time1),
            msg.copy(timestamp = time2),
            msg.copy(timestamp = time3),
            msg.copy(timestamp = time4),
            msg.copy(timestamp = time5)
        )
        val timeThreshold = 50L

        every { timestamp.getOneHourInSeconds } returns timeThreshold
        coEvery { repository.getMessages() } returns messages

        // WHEN
        val flow = runBlocking { interactor.getMessages() }

        // THEN
        launch {
            flow.collect {
                resultList.add(it)
            }
        }

        assertEquals(SYSTEM, resultList[0].holder)
        assertEquals(5, resultList.filter { it.holder == MY }.size)
        assertEquals(messages.size + 1, resultList.size)
    }

    @Test
    fun `GIVEN messages long period send, WHEN get, THEN add system message between`() =
        testDispatcher.runBlockingTest {
            // GIVEN
            val resultList = mutableListOf<ChatDomain>()
            val time1 = ChatMessageTimestampDomain(30L)
            val time2 = ChatMessageTimestampDomain(23L)
            val time3 = ChatMessageTimestampDomain(20L)
            val time4 = ChatMessageTimestampDomain(20L)
            val time5 = ChatMessageTimestampDomain(10L)
            val msg = ChatDomain(
                ChatMessageIdDomain(42L),
                ChatMessageTextDomain("Oe"),
                ChatMessageTimestampDomain(0L),
                MY
            )
            val messages = listOf(
                msg.copy(timestamp = time1),
                msg.copy(timestamp = time2),
                msg.copy(timestamp = time3),
                msg.copy(timestamp = time4),
                msg.copy(timestamp = time5)
            )
            val timeThreshold = 5L

            every { timestamp.getOneHourInSeconds } returns timeThreshold
            coEvery { repository.getMessages() } returns messages

            // WHEN
            val flow = interactor.getMessages()

            // THEN
            launch {
                flow.collect {
                    resultList.add(it)
                }
            }

            assertEquals(3, resultList.filter { it.holder == SYSTEM }.size)
            assertEquals(5, resultList.filter { it.holder == MY }.size)
            assertEquals(messages.size + 3, resultList.size)
        }

    @Test
    fun `GIVEN no previous messages, WHEN get latest, THEN system message`() =
        testDispatcher.runBlockingTest {
            // GIVEN
            var isFirst = true
            val messages = emptyList<ChatDomain>()
            val msg = ChatDomain(
                ChatMessageIdDomain(42L),
                ChatMessageTextDomain("Oe"),
                ChatMessageTimestampDomain(0L),
                MY
            )
            val latestFlow = flow { emit(msg) }

            coEvery { repository.getMessages() } returns messages
            every { repository.getLatest() } returns latestFlow
            interactor.getMessages()

            // WHEN
            val flow = interactor.getLatest()

            // THEN
            launch {
                flow.collect {
                    if (isFirst) {
                        isFirst = false
                        assertEquals(SYSTEM, it.holder)
                    } else {
                        assert(SYSTEM != it.holder)
                    }
                }
            }
        }

    @Test
    fun `GIVEN messages long period, WHEN get latest, THEN system message`() =
        testDispatcher.runBlockingTest {
            // GIVEN
            var isFirst = true
            val time1 = ChatMessageTimestampDomain(30L)
            val time2 = ChatMessageTimestampDomain(50L)
            val msg = ChatDomain(
                ChatMessageIdDomain(42L),
                ChatMessageTextDomain("Oe"),
                ChatMessageTimestampDomain(0L),
                MY
            )
            val messages = listOf(msg.copy(timestamp = time1))
            val latestFlow = flow { emit(msg.copy(timestamp = time2)) }

            val timeThreshold = 10L

            every { timestamp.getOneHourInSeconds } returns timeThreshold
            coEvery { repository.getMessages() } returns messages
            every { repository.getLatest() } returns latestFlow
            interactor.getMessages()

            // WHEN
            val flow = interactor.getLatest()

            // THEN
            launch {
                flow.collect {
                    if (isFirst) {
                        isFirst = false
                        assertEquals(SYSTEM, it.holder)
                    } else {
                        assert(SYSTEM != it.holder)
                    }
                }
            }
        }

    @Test
    fun `GIVEN messages short period, WHEN get latest, THEN no system message`() =
        testDispatcher.runBlockingTest {
            // GIVEN
            val time1 = ChatMessageTimestampDomain(30L)
            val time2 = ChatMessageTimestampDomain(35L)
            val msg = ChatDomain(
                ChatMessageIdDomain(42L),
                ChatMessageTextDomain("Oe"),
                ChatMessageTimestampDomain(0L),
                MY
            )
            val messages = listOf(msg.copy(timestamp = time1))
            val latestFlow = flow { emit(msg.copy(timestamp = time2)) }

            val timeThreshold = 10L

            every { timestamp.getOneHourInSeconds } returns timeThreshold
            coEvery { repository.getMessages() } returns messages
            every { repository.getLatest() } returns latestFlow
            interactor.getMessages()

            // WHEN
            val flow = interactor.getLatest()

            // THEN
            launch {
                flow.collect {
                    assert(SYSTEM != it.holder)
                }
            }
        }
}
