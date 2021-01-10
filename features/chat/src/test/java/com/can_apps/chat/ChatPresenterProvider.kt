package com.can_apps.chat

import com.can_apps.chat.bresenter.ChatPresenter
import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.core.ChatInteractor
import com.can_apps.chat.data.ChatRepository
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.wrappers.CommonTimestampWrapper
import com.can_apps.message_data_source.MessageDatabaseDataSource
import io.mockk.mockk
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

class TestCommonTimestampWrapper : CommonTimestampWrapper {

    override val currentTimeStampMillis: Long
        get() = 0L

    override fun toDate(millis: Long): String = ""
}

class TestCoroutineDispatcherFactory(
    private val testCoroutineDispatcher: TestCoroutineDispatcher
) : CommonCoroutineDispatcherFactory {

    override val IO: CoroutineContext
        get() = testCoroutineDispatcher
    override val UI: CoroutineContext
        get() = testCoroutineDispatcher
}

internal object ChatPresenterProvider {
    internal fun getPresenter(
        testDispatcher: TestCoroutineDispatcher,
        debounceWaitValue: Long,
        dataSource: MessageDatabaseDataSource
    ): ChatContract.Presenter {
        return ChatPresenter(
            interactor = ChatInteractor(
                repository = ChatRepository(
                    messageDataSource = dataSource,
                    mapper = mockk()
                ),
                time = TestCommonTimestampWrapper()
            ),
            mapper = mockk(),
            dispatcher = TestCoroutineDispatcherFactory(testDispatcher),
            debounceWait = debounceWaitValue
        )
    }
}
