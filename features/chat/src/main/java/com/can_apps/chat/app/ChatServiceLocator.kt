package com.can_apps.chat.app

import com.can_apps.chat.app.adapter.ChatAdapter
import com.can_apps.chat.bresenter.ChatModelMapper
import com.can_apps.chat.bresenter.ChatModelMapperDefault
import com.can_apps.chat.bresenter.ChatPresenter
import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.core.ChatInteractor
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryDefault

// open for integration tests
internal open class ChatServiceLocator {

    companion object {

        private const val FIVE_SECOND_IN_MILLIS = 5000L
    }

    fun getPresenter(): ChatContract.Presenter =
        ChatPresenter(
            getInteractor(),
            getDispatcher(),
            getModelMapper(),
            getDebounceWait()
        )

    open fun getDebounceWait(): Long = FIVE_SECOND_IN_MILLIS

    private fun getInteractor(): ChatContract.Interactor = ChatInteractor()

    open fun getDispatcher(): CommonCoroutineDispatcherFactory =
        CommonCoroutineDispatcherFactoryDefault()

    private fun getModelMapper(): ChatModelMapper = ChatModelMapperDefault()

    fun getAdapter(): ChatAdapter = ChatAdapter()
}
