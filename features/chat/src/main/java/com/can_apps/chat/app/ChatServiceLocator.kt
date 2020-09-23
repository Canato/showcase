package com.can_apps.chat.app

import android.content.Context
import com.can_apps.chat.app.adapter.ChatAdapter
import com.can_apps.chat.bresenter.ChatModelMapper
import com.can_apps.chat.bresenter.ChatModelMapperDefault
import com.can_apps.chat.bresenter.ChatPresenter
import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.core.ChatInteractor
import com.can_apps.chat.data.ChatDtoMapper
import com.can_apps.chat.data.ChatDtoMapperDefault
import com.can_apps.chat.data.ChatRepository
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryDefault
import com.can_apps.common.wrappers.CommonTimestampWrapper
import com.can_apps.common.wrappers.CommonTimestampWrapperDefault
import com.can_apps.message_data_source.MessageDatabaseDataSource
import com.can_apps.message_data_source.MessageDatabaseProvider

// open for integration tests
internal open class ChatServiceLocator(
    private val context: Context
) {

    companion object {

        private const val SECOND_IN_MILLIS = 1500L
    }

    fun getPresenter(): ChatContract.Presenter =
        ChatPresenter(
            getInteractor(),
            getDispatcher(),
            getModelMapper(),
            getDebounceWait()
        )

    open fun getTimestamp(): CommonTimestampWrapper =
        CommonTimestampWrapperDefault()

    private fun getRepository(): ChatContract.Repository =
        ChatRepository(
            getMessageDataSource(),
            getDtoMapper()
        )

    open fun getMessageDataSource(): MessageDatabaseDataSource =
        MessageDatabaseProvider.getInstance(context).getMessageDatabaseDataSource()

    private fun getDtoMapper(): ChatDtoMapper =
        ChatDtoMapperDefault()

    open fun getDebounceWait(): Long = SECOND_IN_MILLIS

    private fun getInteractor(): ChatContract.Interactor =
        ChatInteractor(getRepository(), getTimestamp())

    open fun getDispatcher(): CommonCoroutineDispatcherFactory =
        CommonCoroutineDispatcherFactoryDefault()

    private fun getModelMapper(): ChatModelMapper = ChatModelMapperDefault()

    fun getAdapter(): ChatAdapter = ChatAdapter()
}
