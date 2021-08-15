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
import com.can_apps.chat.data.db.MessageDatabaseHandler
import com.can_apps.chat.data.db.MessageDatabaseProvider
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryDefault
import com.can_apps.common.wrappers.CommonTimestampWrapper
import com.can_apps.common.wrappers.CommonTimestampWrapperDefault

// open for integration tests
internal open class ChatServiceLocator(
    private val context: Context
) {

    companion object {

        private const val SECOND_IN_MILLIS =
            2000L // to test 20s tail rule need to increase this number. e.g 120000 (2min)
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
            messageDatabaseHandler = getMessageDatabaseHandler(),
            mapper = getDtoMapper()
        )

    open fun getMessageDatabaseHandler(): MessageDatabaseHandler =
        MessageDatabaseProvider.getInstance(context).getMessageDatabaseHandler()

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
