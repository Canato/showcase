package com.can_apps.chat

import com.can_apps.chat.bresenter.ChatMessageModel
import com.can_apps.chat.bresenter.ChatMessageTextModel
import com.can_apps.chat.bresenter.ChatMessageTimestampModel
import com.can_apps.chat.core.ChatContract
import com.can_apps.message_data_source.MessageDatabaseDataSource
import com.can_apps.message_data_source.MessageDto
import com.can_apps.message_data_source.MessageHolderEnumDto
import com.can_apps.message_data_source.MessageTextDto
import com.can_apps.message_data_source.MessageTimestampDto
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

        const val timestamp = 42L

        private val timestampModel = ChatMessageTimestampModel(timestamp)

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

        val myMsg1 = ChatMessageModel.My(message1, timestampModel)
        val myMsg2 = ChatMessageModel.My(message2, timestampModel)
        val myMsg3 = ChatMessageModel.My(message3, timestampModel)
        val myMsg4 = ChatMessageModel.My(message4, timestampModel)
        val myMsg5 = ChatMessageModel.My(message5, timestampModel)

        val otherMsg1 = ChatMessageModel.Other(ChatMessageTextModel("$text1?"), timestampModel)
        val otherMsg2 = ChatMessageModel.Other(ChatMessageTextModel("$text2?"), timestampModel)
        val otherMsg3 = ChatMessageModel.Other(ChatMessageTextModel("$text3?"), timestampModel)
        val otherMsg4 = ChatMessageModel.Other(ChatMessageTextModel("$text4?"), timestampModel)
        val otherMsg5 = ChatMessageModel.Other(ChatMessageTextModel("$text5?"), timestampModel)

        val myMsgDto1 = MessageDto(
            MessageTextDto(text1),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.MY
        )
        val myMsgDto2 = MessageDto(
            MessageTextDto(text2),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.MY
        )
        val myMsgDto3 = MessageDto(
            MessageTextDto(text3),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.MY
        )
        val myMsgDto4 = MessageDto(
            MessageTextDto(text4),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.MY
        )
        val myMsgDto5 = MessageDto(
            MessageTextDto(text5),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.MY
        )

        val otherMsgDto1 = MessageDto(
            MessageTextDto("$text1?"),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.OTHER
        )
        val otherMsgDto2 = MessageDto(
            MessageTextDto("$text2?"),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.OTHER
        )
        val otherMsgDto3 = MessageDto(
            MessageTextDto("$text3?"),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.OTHER
        )
        val otherMsgDto4 = MessageDto(
            MessageTextDto("$text4?"),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.OTHER
        )
        val otherMsgDto5 = MessageDto(
            MessageTextDto("$text5?"),
            MessageTimestampDto(timestamp),
            MessageHolderEnumDto.OTHER
        )
    }

    @MockK
    private lateinit var dataSource: MessageDatabaseDataSource

    @MockK
    private lateinit var view: ChatContract.View

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var presenter: ChatContract.Presenter

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        val serviceLocator =
            MockChatServiceLocator(testDispatcher, debounceWait, dataSource, timestamp)

        presenter = serviceLocator.getPresenter()

        presenter.bind(view)
    }

    @Test
    fun `WHEN no wait period, THEN return last one in question`() =
        testDispatcher.runBlockingTest {
            // WHEN
            presenter.onSendMessage(message1)
            presenter.onSendMessage(message2)
            presenter.onSendMessage(message3)
            presenter.onSendMessage(message4)
            presenter.onSendMessage(message5)
            testDispatcher.advanceTimeBy(debounceWait * 2)

            // THEN
            coVerify(exactly = 1) {
                dataSource.add(myMsgDto1)
                dataSource.add(myMsgDto1)
                dataSource.add(myMsgDto1)
                dataSource.add(myMsgDto4)
                dataSource.add(myMsgDto5)
                dataSource.add(otherMsgDto5)
            }

            coVerify(exactly = 0) {
                dataSource.add(otherMsgDto1)
                dataSource.add(otherMsgDto2)
                dataSource.add(otherMsgDto3)
                dataSource.add(otherMsgDto4)
            }
        }

    @Test
    fun `WHEN small wait period, THEN return last one in question`() =
        testDispatcher.runBlockingTest {
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
                dataSource.add(myMsgDto1)
                dataSource.add(myMsgDto2)
                dataSource.add(myMsgDto3)
                dataSource.add(myMsgDto4)
                dataSource.add(myMsgDto5)
                dataSource.add(otherMsgDto5)
            }

            coVerify(exactly = 0) {
                dataSource.add(otherMsgDto1)
                dataSource.add(otherMsgDto2)
                dataSource.add(otherMsgDto3)
                dataSource.add(otherMsgDto4)
            }
        }

    @Test
    fun `WHEN small and more wait period, THEN return some question`() =
        testDispatcher.runBlockingTest {
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
                dataSource.add(myMsgDto1)
                dataSource.add(myMsgDto2)
                dataSource.add(myMsgDto3)
                dataSource.add(myMsgDto4)
                dataSource.add(myMsgDto5)
                dataSource.add(otherMsgDto2)
                dataSource.add(otherMsgDto3)
                dataSource.add(otherMsgDto5)
            }

            coVerify(exactly = 0) {
                dataSource.add(otherMsgDto1)
                dataSource.add(otherMsgDto4)
            }
        }

    @Test
    fun `GIVEN messages, WHEN on create, THEN update list and listen to db`() =
        testDispatcher.runBlockingTest {
            // GIVEN
            val messagesDto = listOf(myMsgDto1, myMsgDto3, otherMsgDto3, myMsgDto5, otherMsgDto1)
            val messagesModel = listOf(myMsg1, myMsg3, otherMsg3, myMsg5, otherMsg1)
            val databaseFlowChange = flow {
                emit(myMsgDto2)
                emit(myMsgDto4)
                emit(otherMsgDto5)
                emit(otherMsgDto2)
                emit(otherMsgDto4)
            }
            coEvery { dataSource.getAll() } returns messagesDto
            coEvery { dataSource.getLatestValue() } returns databaseFlowChange

            // WHEN
            presenter.onViewCreated()

            // THEN
            verifySequence {
                view.setupMessages(messagesModel)
                view.addMessage(myMsg2)
                view.addMessage(myMsg4)
                view.addMessage(otherMsg5)
                view.addMessage(otherMsg2)
                view.addMessage(otherMsg4)
            }
        }
}
