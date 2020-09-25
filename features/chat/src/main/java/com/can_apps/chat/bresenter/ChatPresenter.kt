package com.can_apps.chat.bresenter

import com.can_apps.chat.core.ChatContract
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class ChatPresenter(
    private val interactor: ChatContract.Interactor,
    private val dispatcher: CommonCoroutineDispatcherFactory,
    private val mapper: ChatModelMapper,
    private val debounceWait: Long,
) : ChatContract.Presenter, CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher.UI + job

    private val channel = BroadcastChannel<ChatMessageTextModel>(Channel.CONFLATED)

    private var view: ChatContract.View? = null

    @FlowPreview
    override fun bind(view: ChatContract.View) {
        this.view = view
        getAnswer(channel)
    }

    override fun unbind() {
        view = null
        job.cancel()
    }

    override fun onViewCreated() {
        fetchMessages()
    }

    override fun onSendMessage(message: ChatMessageTextModel) {
        if (message.value.isNotBlank()) {
            channel.offer(message)
            saveMessage(message)
        }
    }

    private fun CoroutineScope.saveMessage(message: ChatMessageTextModel) =
        launch(dispatcher.IO) {
            val domain = mapper.toMyDomain(message)
            interactor.addMessage(domain)
        }

    private fun CoroutineScope.fetchMessages() = launch {
        interactor.getMessages()
            .flowOn(dispatcher.IO)
            .collect {
                view?.addMessage(mapper.toModel(it))
            }
        setupMessagesListener()
    }

    @FlowPreview
    private fun CoroutineScope.getAnswer(
        receiveChannel: BroadcastChannel<ChatMessageTextModel>
    ) = launch(dispatcher.IO) {
        receiveChannel
            .asFlow()
            .debounce(debounceWait)
            .collect { message ->
                val domain = mapper.toOtherDomain(message)
                val answerMsg = interactor.getSystemAnswer(domain)
                interactor.addMessage(answerMsg)
            }
    }

    private fun CoroutineScope.setupMessagesListener() = launch {
        interactor
            .getLatest()
            .flowOn(dispatcher.IO)
            .collect {
                view?.addMessage(mapper.toModel(it))
            }
    }
}
