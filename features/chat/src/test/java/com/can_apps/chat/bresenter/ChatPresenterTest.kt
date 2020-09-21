package com.can_apps.chat.bresenter

import com.can_apps.chat.core.ChatContract
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryUnconfined
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

internal class ChatPresenterTest {

    @MockK
    private lateinit var interactor: ChatContract.Interactor

    @MockK
    private lateinit var dispatcher: CommonCoroutineDispatcherFactory

    @MockK
    private lateinit var mapper: ChatModelMapper

    @MockK
    private lateinit var view: ChatContract.View

    private lateinit var presenter: ChatPresenter

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        presenter = ChatPresenter(interactor, dispatcher, mapper, 1L)

        val unconfinedFactory = CommonCoroutineDispatcherFactoryUnconfined()
        every { dispatcher.IO } returns unconfinedFactory.IO
        every { dispatcher.UI } returns unconfinedFactory.UI

        presenter.bind(view)
    }

    @Test
    fun `WHEN view create, THEN setup list`() {
        // WHEN
        presenter.onViewCreated()

        // THEN
        verify {
            view.setupMessages(emptyList())
        }
    }

    @Test
    fun `GIVEN message, WHEN send, THEN add to list`() {
        // GIVEN
        val message = "Poesia Acustica"
        val messageModel = ChatMessageTextModel(message)
        val expect = ChatMessageModel.My(messageModel)

        // WHEN
        presenter.onSendMessage(messageModel)

        // THEN
        verify {
            view.addMessage(expect)
        }
    }

    @Test
    fun `GIVEN message empty, WHEN send, THEN do nothing`() {
        // GIVEN
        val message = ""
        val messageModel = ChatMessageTextModel(message)
        val expect = ChatMessageModel.My(messageModel)

        // WHEN
        presenter.onSendMessage(messageModel)

        // THEN
        verify(exactly = 0) {
            view.addMessage(expect)
        }
    }
}
