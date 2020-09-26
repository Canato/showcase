package com.can_apps.chat.core

import com.can_apps.chat.core.ChatMessageHolderEnumDomain.MY
import com.can_apps.chat.core.ChatMessageHolderEnumDomain.OTHER
import com.can_apps.chat.core.ChatMessageHolderEnumDomain.SYSTEM
import com.can_apps.common.wrappers.CommonTimestampWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
    fun `GIVEN msg with long gap, WHEN add message, THEN previous with tail`() {
        // GIVEN
        val newText = ChatMessageTextDomain("Pinca")
        val newHolder = MY
        val newMessage = ChatNewDomain(newText, newHolder)
        val newTimestamp = 80000L

        val prevId = ChatMessageIdDomain(42L)
        val prevText = ChatMessageTextDomain("Tormenta")
        val prevTime = ChatMessageTimestampDomain(50000L)
        val prevHolder = MY
        val prevTail = ChatMessageTailDomain(false)
        val prevMessage = ChatDomain(prevId, prevText, prevTime, prevHolder, prevTail)

        val expected = prevMessage.copy(hasTail = ChatMessageTailDomain(true))

        coEvery { repository.getLatest() } returns prevMessage
        every { timestamp.currentTimeStampMillis } returns newTimestamp

        // WHEN
        runBlocking { interactor.addMessage(newMessage) }

        // THEN
        coVerify {
            repository.updateAndAddMessage(expected, newMessage)
        }
    }

    @Test
    fun `GIVEN msg with small gap, WHEN add message, THEN previous without tail`() {
        // GIVEN
        val newText = ChatMessageTextDomain("Pinca")
        val newHolder = MY
        val newMessage = ChatNewDomain(newText, newHolder)
        val newTimestamp = 60L

        val prevId = ChatMessageIdDomain(42L)
        val prevText = ChatMessageTextDomain("Tormenta")
        val prevTime = ChatMessageTimestampDomain(50L)
        val prevHolder = MY
        val prevTail = ChatMessageTailDomain(false)
        val prevMessage = ChatDomain(prevId, prevText, prevTime, prevHolder, prevTail)

        val expected = prevMessage.copy(hasTail = ChatMessageTailDomain(false))

        coEvery { repository.getLatest() } returns prevMessage
        every { timestamp.currentTimeStampMillis } returns newTimestamp

        // WHEN
        runBlocking { interactor.addMessage(newMessage) }

        // THEN
        coVerify {
            repository.updateAndAddMessage(expected, newMessage)
        }
    }

    @Test
    fun `GIVEN previous msg another holder, WHEN add message, THEN do nothing`() {
        // GIVEN
        val newText = ChatMessageTextDomain("Pinca")
        val newHolder = MY
        val newMessage = ChatNewDomain(newText, newHolder)
        val newTimestamp = 60L

        val prevId = ChatMessageIdDomain(42L)
        val prevText = ChatMessageTextDomain("Tormenta")
        val prevTime = ChatMessageTimestampDomain(50L)
        val prevHolder = OTHER
        val prevTail = ChatMessageTailDomain(false)
        val prevMessage = ChatDomain(prevId, prevText, prevTime, prevHolder, prevTail)

        val expected = prevMessage.copy(hasTail = ChatMessageTailDomain(false))

        coEvery { repository.getLatest() } returns prevMessage
        every { timestamp.currentTimeStampMillis } returns newTimestamp

        // WHEN
        runBlocking { interactor.addMessage(newMessage) }

        // THEN
        coVerify {
            repository.addMessage(newMessage)
        }
        coVerify(exactly = 0) {
            repository.updateAndAddMessage(expected, newMessage)
        }
    }

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
        testDispatcher.runBlockingTest {
            // GIVEN
            val resultList = mutableListOf<ChatDomain>()
            val time1 = ChatMessageTimestampDomain(3650000)
            val time2 = ChatMessageTimestampDomain(3640000)
            val time3 = ChatMessageTimestampDomain(3630000)
            val time4 = ChatMessageTimestampDomain(3630000)
            val time5 = ChatMessageTimestampDomain(3620000)
            val msg = ChatDomain(
                ChatMessageIdDomain(42L),
                ChatMessageTextDomain("Oe"),
                ChatMessageTimestampDomain(0L),
                MY,
                ChatMessageTailDomain(false)
            )
            val messages = listOf(
                msg.copy(timestamp = time1),
                msg.copy(timestamp = time2),
                msg.copy(timestamp = time3),
                msg.copy(timestamp = time4),
                msg.copy(timestamp = time5)
            )

            coEvery { repository.getMessages() } returns messages

            // WHEN
            val flow = interactor.getMessagesFlow()

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
            val time1 = ChatMessageTimestampDomain(53600000L)
            val time2 = ChatMessageTimestampDomain(43601000L)
            val time3 = ChatMessageTimestampDomain(43300000L)
            val time4 = ChatMessageTimestampDomain(33603000L)
            val time5 = ChatMessageTimestampDomain(33601000L)
            val msg = ChatDomain(
                ChatMessageIdDomain(42L),
                ChatMessageTextDomain("Oe"),
                ChatMessageTimestampDomain(0L),
                MY,
                ChatMessageTailDomain(false)
            )
            val messages = listOf(
                msg.copy(timestamp = time1),
                msg.copy(timestamp = time2),
                msg.copy(timestamp = time3),
                msg.copy(timestamp = time4),
                msg.copy(timestamp = time5)
            )

            coEvery { repository.getMessages() } returns messages

            // WHEN
            val flow = interactor.getMessagesFlow()

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
            val resultList = mutableListOf<ChatDomain>()
            val messages = emptyList<ChatDomain>()
            val msg = ChatDomain(
                ChatMessageIdDomain(42L),
                ChatMessageTextDomain("Oe"),
                ChatMessageTimestampDomain(73600000L),
                MY,
                ChatMessageTailDomain(false)
            )
            val latestFlow = flow { emit(msg) }

            coEvery { repository.getMessages() } returns messages
            every { repository.getLatestFlow() } returns latestFlow
            val check = interactor.getMessagesFlow()
            launch { check.collect { } }

            // WHEN
            val flow = interactor.getLatestFlow()

            // THEN
            launch {
                flow.collect {
                    resultList.add(it)
                }
            }

            assertEquals(SYSTEM, resultList[0].holder)
            assertEquals(MY, resultList[1].holder)
        }

    @Test
    fun `GIVEN messages long period, WHEN get latest, THEN system message`() =
        testDispatcher.runBlockingTest {
            // GIVEN
            var isFirst = true
            val time1 = ChatMessageTimestampDomain(23601000L)
            val time2 = ChatMessageTimestampDomain(13600000L)
            val msg = ChatDomain(
                ChatMessageIdDomain(42L),
                ChatMessageTextDomain("Oe"),
                ChatMessageTimestampDomain(0L),
                MY,
                ChatMessageTailDomain(false)
            )
            val messages = listOf(msg.copy(timestamp = time2))
            val latestFlow = flow { emit(msg.copy(timestamp = time1)) }

            coEvery { repository.getMessages() } returns messages
            every { repository.getLatestFlow() } returns latestFlow
            val check = interactor.getMessagesFlow()
            launch { check.collect { } }

            // WHEN
            val flow = interactor.getLatestFlow()

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
            val time1 = ChatMessageTimestampDomain(3650L)
            val time2 = ChatMessageTimestampDomain(3610L)
            val msg = ChatDomain(
                ChatMessageIdDomain(42L),
                ChatMessageTextDomain("Oe"),
                ChatMessageTimestampDomain(0L),
                MY,
                ChatMessageTailDomain(false)
            )
            val messages = listOf(msg.copy(timestamp = time1))
            val latestFlow = flow { emit(msg.copy(timestamp = time2)) }

            coEvery { repository.getMessages() } returns messages
            every { repository.getLatestFlow() } returns latestFlow
            val check = interactor.getMessagesFlow()
            launch { check.collect { } }

            // WHEN
            val flow = interactor.getLatestFlow()

            // THEN
            launch {
                flow.collect {
                    assert(SYSTEM != it.holder)
                }
            }
        }
}
