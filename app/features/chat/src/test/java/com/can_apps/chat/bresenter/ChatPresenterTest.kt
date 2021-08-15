package com.can_apps.chat.bresenter

import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.core.ChatDomain
import com.can_apps.chat.core.ChatMessageHolderEnumDomain
import com.can_apps.chat.core.ChatMessageTextDomain
import com.can_apps.chat.core.ChatNewDomain
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryUnconfined
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
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

    private val testDispatcher = TestCoroutineDispatcher()

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
    fun `GIVEN messages, WHEN view create, THEN setup list`() =
        testDispatcher.runBlockingTest {
            // GIVEN
            val domain = mockk<ChatDomain>(relaxed = true)
            val messages = flow { emit(domain) }
            val expected = mockk<ChatMessageModel>(relaxed = true)

            coEvery { interactor.getMessagesFlow() } returns messages
            every { mapper.toModel(domain) } returns expected

            // WHEN
            presenter.onViewCreated()

            // THEN
            verify {
                view.addMessage(expected)
            }
        }

    @Test
    fun `GIVEN message empty, WHEN send, THEN do nothing`() {
        // GIVEN
        val message = ""
        val messageModel = ChatMessageTextModel(message)
        val messageDomain = ChatMessageTextDomain(message)
        val expect = ChatNewDomain(messageDomain, ChatMessageHolderEnumDomain.MY)

        // WHEN
        presenter.onSendMessage(messageModel)

        // THEN
        coVerify(exactly = 0) {
            interactor.addMessage(expect)
        }
    }
}
