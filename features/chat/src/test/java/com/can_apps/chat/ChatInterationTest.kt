package com.can_apps.chat

import com.can_apps.chat.bresenter.ChatMessageModel
import com.can_apps.chat.bresenter.ChatMessageTextModel
import com.can_apps.chat.core.ChatContract
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

        val model1 = ChatMessageModel.My(message1)
        val model2 = ChatMessageModel.My(message2)
        val model3 = ChatMessageModel.My(message3)
        val model4 = ChatMessageModel.My(message4)
        val model5 = ChatMessageModel.My(message5)

        val modelQuestion1 = ChatMessageModel.Other(ChatMessageTextModel("$text1?"))
        val modelQuestion2 = ChatMessageModel.Other(ChatMessageTextModel("$text2?"))
        val modelQuestion3 = ChatMessageModel.Other(ChatMessageTextModel("$text3?"))
        val modelQuestion4 = ChatMessageModel.Other(ChatMessageTextModel("$text4?"))
        val modelQuestion5 = ChatMessageModel.Other(ChatMessageTextModel("$text5?"))
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
            testDispatcher.advanceTimeBy(debounceWait*2)

            // THEN
            verify(exactly = 1) {
                view.addMessage(model1)
                view.addMessage(model2)
                view.addMessage(model3)
                view.addMessage(model4)
                view.addMessage(model5)
                view.addMessage(modelQuestion5)
            }

            verify(exactly = 0) {
                view.addMessage(modelQuestion1)
                view.addMessage(modelQuestion2)
                view.addMessage(modelQuestion3)
                view.addMessage(modelQuestion4)
            }
        }

    @Test
    fun `WHEN small wait period, THEN return last one in question`() =
        testDispatcher.runBlockingTest {
            // WHEN
            presenter.onSendMessage(message1)
            testDispatcher.advanceTimeBy(debounceWait/2)
            presenter.onSendMessage(message2)
            testDispatcher.advanceTimeBy(debounceWait/2)
            presenter.onSendMessage(message3)
            testDispatcher.advanceTimeBy(debounceWait/2)
            presenter.onSendMessage(message4)
            testDispatcher.advanceTimeBy(debounceWait/2)
            presenter.onSendMessage(message5)
            testDispatcher.advanceTimeBy(debounceWait)

            // THEN
            verify(exactly = 1) {
                view.addMessage(model1)
                view.addMessage(model2)
                view.addMessage(model3)
                view.addMessage(model4)
                view.addMessage(model5)
                view.addMessage(modelQuestion5)
            }

            verify(exactly = 0) {
                view.addMessage(modelQuestion1)
                view.addMessage(modelQuestion2)
                view.addMessage(modelQuestion3)
                view.addMessage(modelQuestion4)
            }
        }

    @Test
    fun `WHEN small and more wait period, THEN return some question`() =
        testDispatcher.runBlockingTest {
            // WHEN
            presenter.onSendMessage(message1)
            testDispatcher.advanceTimeBy(debounceWait/2)
            presenter.onSendMessage(message2)
            testDispatcher.advanceTimeBy(debounceWait*2)
            presenter.onSendMessage(message3)
            testDispatcher.advanceTimeBy(debounceWait*2)
            presenter.onSendMessage(message4)
            testDispatcher.advanceTimeBy(debounceWait/2)
            presenter.onSendMessage(message5)
            testDispatcher.advanceTimeBy(debounceWait*2)

            // THEN
            verify(exactly = 1) {
                view.addMessage(model1)
                view.addMessage(model2)
                view.addMessage(model3)
                view.addMessage(model4)
                view.addMessage(model5)
                view.addMessage(modelQuestion2)
                view.addMessage(modelQuestion3)
                view.addMessage(modelQuestion5)
            }

            verify(exactly = 0) {
                view.addMessage(modelQuestion1)
                view.addMessage(modelQuestion4)
            }
        }
}