package com.can_apps.chat.app

import com.can_apps.chat.app.adapter.ChatAdapter
import com.can_apps.chat.bresenter.ChatModelMapper
import com.can_apps.chat.bresenter.ChatModelMapperDefault
import com.can_apps.chat.bresenter.ChatPresenter
import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.core.ChatInteractor
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryDefault

internal class ChatServiceLocator {

    fun getPresenter(): ChatContract.Presenter =
        ChatPresenter(
            getInteractor(),
            getDispatcher(),
            getModelMapper()
        )

    private fun getInteractor(): ChatContract.Interactor =
        ChatInteractor()

    private fun getDispatcher(): CommonCoroutineDispatcherFactory =
        CommonCoroutineDispatcherFactoryDefault()

    private fun getModelMapper(): ChatModelMapper =
        ChatModelMapperDefault()

    fun getAdapter(): ChatAdapter =
        ChatAdapter()
}
