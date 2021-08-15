package com.can_apps.chat

import com.can_apps.chat.bresenter.ChatMessageIdModel
import com.can_apps.chat.bresenter.ChatMessageModel
import com.can_apps.chat.bresenter.ChatMessageTextModel
import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.data.MessageDto
import com.can_apps.chat.data.MessageHolderEnumDto
import com.can_apps.chat.data.MessageIdDto
import com.can_apps.chat.data.MessageTailDto
import com.can_apps.chat.data.MessageTextDto
import com.can_apps.chat.data.MessageTimestampDto
import com.can_apps.chat.data.NewMessageDto
import com.can_apps.chat.data.db.MessageDatabaseHandler
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

internal class ChatIntegrationTest {

    companion object {

        private const val debounceWait = 100L
        private const val timestamp = 4200000L

        private val id = ChatMessageIdModel(42L)

        private const val text1 = "One"
        private const val text2 = "Two"
        private const val text3 = "Three"
        private const val text4 = "Four"
        private const val text5 = "Five"

        val message1 = ChatMessageTextModel(text1)
        val message2 = ChatMessageTextModel(text2)
        val message3 = ChatMessageTextModel(text3)
        val message4 = ChatMessageTextModel(text4)
        val message5 = ChatMessageTextModel(text5)

        val myMsg1 = ChatMessageModel.My(id, message1)
        val myMsg2 = ChatMessageModel.My(id, message2)
        val myMsg3 = ChatMessageModel.My(id, message3)
        val myMsg4 = ChatMessageModel.My(id, message4)
        val myMsg5 = ChatMessageModel.My(id, message5)

        val otherMsg1 = ChatMessageModel.Other(id, ChatMessageTextModel("$text1?"))
        val otherMsg2 = ChatMessageModel.Other(id, ChatMessageTextModel("$text2?"))
        val otherMsg3 = ChatMessageModel.Other(id, ChatMessageTextModel("$text3?"))
        val otherMsg4 = ChatMessageModel.Other(id, ChatMessageTextModel("$text4?"))
        val otherMsg5 = ChatMessageModel.Other(id, ChatMessageTextModel("$text5?"))

        val newMyMsgDto1 = NewMessageDto(MessageTextDto(text1), MessageHolderEnumDto.MY)
        val newMyMsgDto2 = NewMessageDto(MessageTextDto(text2), MessageHolderEnumDto.MY)
        val newMyMsgDto3 = NewMessageDto(MessageTextDto(text3), MessageHolderEnumDto.MY)
        val newMyMsgDto4 = NewMessageDto(MessageTextDto(text4), MessageHolderEnumDto.MY)
        val newMyMsgDto5 = NewMessageDto(MessageTextDto(text5), MessageHolderEnumDto.MY)

        val newOtherMsgDto1 = NewMessageDto(MessageTextDto("$text1?"), MessageHolderEnumDto.OTHER)
        val newOtherMsgDto2 = NewMessageDto(MessageTextDto("$text2?"), MessageHolderEnumDto.OTHER)
        val newOtherMsgDto3 = NewMessageDto(MessageTextDto("$text3?"), MessageHolderEnumDto.OTHER)
        val newOtherMsgDto4 = NewMessageDto(MessageTextDto("$text4?"), MessageHolderEnumDto.OTHER)
        val newOtherMsgDto5 = NewMessageDto(MessageTextDto("$text5?"), MessageHolderEnumDto.OTHER)

        val myMsgDto1 = MessageDto(
            MessageIdDto(id.value),
            MessageTextDto(text1),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.MY,
            MessageTailDto(false)
        )
        val myMsgDto2 = MessageDto(
            MessageIdDto(id.value),
            MessageTextDto(text2),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.MY,
            MessageTailDto(false)
        )
        val myMsgDto3 = MessageDto(
            MessageIdDto(id.value),
            MessageTextDto(text3),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.MY,
            MessageTailDto(false)
        )
        val myMsgDto4 = MessageDto(
            MessageIdDto(id.value),
            MessageTextDto(text4),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.MY,
            MessageTailDto(false)
        )
        val myMsgDto5 = MessageDto(
            MessageIdDto(id.value),
            MessageTextDto(text5),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.MY,
            MessageTailDto(false)
        )

        val otherMsgDto1 = MessageDto(
            MessageIdDto(id.value),
            MessageTextDto("$text1?"),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.OTHER,
            MessageTailDto(false)
        )
        val otherMsgDto2 = MessageDto(
            MessageIdDto(id.value),
            MessageTextDto("$text2?"),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.OTHER,
            MessageTailDto(false)
        )
        val otherMsgDto3 = MessageDto(
            MessageIdDto(id.value),
            MessageTextDto("$text3?"),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.OTHER,
            MessageTailDto(false)
        )
        val otherMsgDto4 = MessageDto(
            MessageIdDto(id.value),
            MessageTextDto("$text4?"),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.OTHER,
            MessageTailDto(false)
        )
        val otherMsgDto5 = MessageDto(
            MessageIdDto(id.value),
            MessageTextDto("$text5?"),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.OTHER,
            MessageTailDto(false)
        )
    }

    @MockK
    private lateinit var databaseHandler: MessageDatabaseHandler

    @MockK
    private lateinit var view: ChatContract.View

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var presenter: ChatContract.Presenter

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        val serviceLocator = MockChatServiceLocator(testDispatcher, debounceWait, databaseHandler)

        presenter = serviceLocator.getPresenter()

        presenter.bind(view)
    }

    @Test
    fun `WHEN no wait period, THEN return last one in question`() =
        testDispatcher.runBlockingTest {
            // GIVEN
            coEvery { databaseHandler.getLatestValue() } returns null

            // WHEN
            presenter.onSendMessage(message1)
            presenter.onSendMessage(message2)
            presenter.onSendMessage(message3)
            presenter.onSendMessage(message4)
            presenter.onSendMessage(message5)
            testDispatcher.advanceTimeBy(debounceWait * 2)

            coEvery { databaseHandler.add(any()) } returns true

            // THEN
            coVerify(exactly = 1) {
                databaseHandler.add(newMyMsgDto1)
                databaseHandler.add(newMyMsgDto1)
                databaseHandler.add(newMyMsgDto1)
                databaseHandler.add(newMyMsgDto4)
                databaseHandler.add(newMyMsgDto5)
                databaseHandler.add(newOtherMsgDto5)
            }

            coVerify(exactly = 0) {
                databaseHandler.add(newOtherMsgDto1)
                databaseHandler.add(newOtherMsgDto2)
                databaseHandler.add(newOtherMsgDto3)
                databaseHandler.add(newOtherMsgDto4)
            }
        }

    @Test
    fun `WHEN small wait period, THEN return last one in question`() =
        testDispatcher.runBlockingTest {
            // GIVEN
            coEvery { databaseHandler.getLatestValue() } returns null

            // WHEN
            presenter.onSendMessage(message1)
            testDispatcher.advanceTimeBy(debounceWait / 2)
            presenter.onSendMessage(message2)
            testDispatcher.advanceTimeBy(debounceWait / 2)
            presenter.onSendMessage(message3)
            testDispatcher.advanceTimeBy(debounceWait / 2)
            presenter.onSendMessage(message4)
            testDispatcher.advanceTimeBy(debounceWait / 2)
            presenter.onSendMessage(message5)
            testDispatcher.advanceTimeBy(debounceWait)

            // THEN
            coVerify(exactly = 1) {
                databaseHandler.add(newMyMsgDto1)
                databaseHandler.add(newMyMsgDto2)
                databaseHandler.add(newMyMsgDto3)
                databaseHandler.add(newMyMsgDto4)
                databaseHandler.add(newMyMsgDto5)
                databaseHandler.add(newOtherMsgDto5)
            }

            coVerify(exactly = 0) {
                databaseHandler.add(newOtherMsgDto1)
                databaseHandler.add(newOtherMsgDto2)
                databaseHandler.add(newOtherMsgDto3)
                databaseHandler.add(newOtherMsgDto4)
            }
        }

    @Test
    fun `WHEN small and more wait period, THEN return some question`() =
        testDispatcher.runBlockingTest {
            // GIVEN
            coEvery { databaseHandler.getLatestValue() } returns null

            // WHEN
            presenter.onSendMessage(message1)
            testDispatcher.advanceTimeBy(debounceWait / 2)
            presenter.onSendMessage(message2)
            testDispatcher.advanceTimeBy(debounceWait * 2)
            presenter.onSendMessage(message3)
            testDispatcher.advanceTimeBy(debounceWait * 2)
            presenter.onSendMessage(message4)
            testDispatcher.advanceTimeBy(debounceWait / 2)
            presenter.onSendMessage(message5)
            testDispatcher.advanceTimeBy(debounceWait * 2)

            // THEN
            coVerify(exactly = 1) {
                databaseHandler.add(newMyMsgDto1)
                databaseHandler.add(newMyMsgDto2)
                databaseHandler.add(newMyMsgDto3)
                databaseHandler.add(newMyMsgDto4)
                databaseHandler.add(newMyMsgDto5)
                databaseHandler.add(newOtherMsgDto2)
                databaseHandler.add(newOtherMsgDto3)
                databaseHandler.add(newOtherMsgDto5)
            }

            coVerify(exactly = 0) {
                databaseHandler.add(newOtherMsgDto1)
                databaseHandler.add(newOtherMsgDto4)
            }
        }

    @Test
    fun `GIVEN messages, WHEN on create, THEN update list and listen to db`() =
        testDispatcher.runBlockingTest {
            // GIVEN
            val messagesDto = listOf(myMsgDto1, myMsgDto3, otherMsgDto3, myMsgDto5, otherMsgDto1)
            val databaseFlowChange = flow {
                emit(myMsgDto2)
                emit(myMsgDto4)
                emit(otherMsgDto5)
                emit(otherMsgDto2)
                emit(otherMsgDto4)
            }
            coEvery { databaseHandler.getAll() } returns messagesDto
            coEvery { databaseHandler.getLatestValueFlow() } returns databaseFlowChange

            // WHEN
            presenter.onViewCreated()

            // THEN
            verifySequence {
                view.addMessage(ChatMessageModel.System(ChatMessageIdModel(timestamp), ChatMessageTextModel("")))
                view.addMessage(otherMsg1)
                view.addMessage(myMsg5)
                view.addMessage(otherMsg3)
                view.addMessage(myMsg3)
                view.addMessage(myMsg1)
                view.addMessage(myMsg2)
                view.addMessage(myMsg4)
                view.addMessage(otherMsg5)
                view.addMessage(otherMsg2)
                view.addMessage(otherMsg4)
            }
        }
}
