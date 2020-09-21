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
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class ChatPresenter(
    private val interactor: ChatContract.Interactor,
    private val dispatcher: CommonCoroutineDispatcherFactory,
    private val mapper: ChatModelMapper,
    private val debounceWait: Long = FIVE_SECOND_IN_MILLIS
) : ChatContract.Presenter, CoroutineScope {

    companion object {

        private const val FIVE_SECOND_IN_MILLIS = 5000L
    }

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
        view?.setupMessages(emptyList())
    }

    override fun onSendMessage(message: ChatMessageTextModel) {
        channel.offer(message)
        if (message.value.isNotBlank()) view?.addMessage(ChatMessageModel.My(message))
    }

    @FlowPreview
    private fun CoroutineScope.getAnswer(
        receiveChannel: BroadcastChannel<ChatMessageTextModel>
    ) = launch(dispatcher.IO) {
        receiveChannel
            .asFlow()
            .debounce(debounceWait)
            .collect { message ->
                val domain = interactor.getSystemAnswer(mapper.toDomain(message))
                onAnswerMessage(mapper.toOtherModel(domain))
            }
    }

    private fun CoroutineScope.onAnswerMessage(model: ChatMessageModel) =
        launch(dispatcher.UI) {
            view?.addMessage(model)
        }
}
