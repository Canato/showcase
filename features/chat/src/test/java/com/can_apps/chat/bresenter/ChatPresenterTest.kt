package com.can_apps.chat.bresenter

import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.core.ChatDomain
import com.can_apps.chat.core.ChatMessageHolderEnumDto
import com.can_apps.chat.core.ChatMessageTextDomain
import com.can_apps.chat.core.ChatMessageTimestampDomain
import com.can_apps.chat.core.ChatNewDomain
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryUnconfined
import com.can_apps.common.wrappers.CommonTimestampWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
    fun `GIVEN messages, WHEN view create, THEN setup list`() {
        // GIVEN
        val messages = mockk<List<ChatDomain>>(relaxed = true)
        val expected = mockk<List<ChatMessageModel>>(relaxed = true)
        coEvery { interactor.getMessages() } returns messages
        every { mapper.toModel(messages) } returns expected

        // WHEN
        presenter.onViewCreated()

        // THEN
        verify {
            view.setupMessages(expected)
        }
    }

    @Test
    fun `GIVEN message empty, WHEN send, THEN do nothing`() {
        // GIVEN
        val message = ""
        val messageModel = ChatMessageTextModel(message)
        val messageDomain = ChatMessageTextDomain(message)
        val expect = ChatNewDomain(messageDomain, ChatMessageHolderEnumDto.MY)

        // WHEN
        presenter.onSendMessage(messageModel)

        // THEN
        coVerify(exactly = 0) {
            interactor.addMessage(expect)
        }
    }
}
