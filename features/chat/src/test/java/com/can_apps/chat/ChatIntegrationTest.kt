package com.can_apps.chat

import com.can_apps.chat.bresenter.ChatMessageModel
import com.can_apps.chat.bresenter.ChatMessageTextModel
import com.can_apps.chat.bresenter.ChatMessageTimestampModel
import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.core.ChatMessageTimestampDomain
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

internal class ChatIntegrationTest {

    companion object {

        private const val debounceWait = 100L

        private val timestampModel = ChatMessageTimestampModel(42L)

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
    }

    @MockK
    private lateinit var view: ChatContract.View

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var presenter: ChatContract.Presenter

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        val serviceLocator = MockChatServiceLocator(testDispatcher, debounceWait)

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
            verify(exactly = 1) {
                view.addMessage(myMsg1)
                view.addMessage(myMsg2)
                view.addMessage(myMsg3)
                view.addMessage(myMsg4)
                view.addMessage(myMsg5)
                view.addMessage(otherMsg5)
            }

            verify(exactly = 0) {
                view.addMessage(otherMsg1)
                view.addMessage(otherMsg2)
                view.addMessage(otherMsg3)
                view.addMessage(otherMsg4)
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
            verify(exactly = 1) {
                view.addMessage(myMsg1)
                view.addMessage(myMsg2)
                view.addMessage(myMsg3)
                view.addMessage(myMsg4)
                view.addMessage(myMsg5)
                view.addMessage(otherMsg5)
            }

            verify(exactly = 0) {
                view.addMessage(otherMsg1)
                view.addMessage(otherMsg2)
                view.addMessage(otherMsg3)
                view.addMessage(otherMsg4)
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
            verify(exactly = 1) {
                view.addMessage(myMsg1)
                view.addMessage(myMsg2)
                view.addMessage(myMsg3)
                view.addMessage(myMsg4)
                view.addMessage(myMsg5)
                view.addMessage(otherMsg2)
                view.addMessage(otherMsg3)
                view.addMessage(otherMsg5)
            }

            verify(exactly = 0) {
                view.addMessage(otherMsg1)
                view.addMessage(otherMsg4)
            }
        }
}
