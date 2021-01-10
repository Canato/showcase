package com.can_apps.chat

import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.wrappers.CommonTimestampWrapper
import com.can_apps.message_data_source.MessageDatabaseDataSource
import io.mockk.mockk
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

internal class MockChatServiceLocator(
    private val testDispatcher: TestCoroutineDispatcher,
    private val debounceWaitValue: Long,
    private val dataSource: MessageDatabaseDataSource
) : ChatServiceLocator(mockk(relaxed = true)) {

    override fun getDispatcher(): CommonCoroutineDispatcherFactory =
        TestCoroutineDispatcherFactory(testDispatcher)

    override fun getDebounceWait(): Long = debounceWaitValue

    override fun getMessageDataSource(): MessageDatabaseDataSource = dataSource

    override fun getTimestamp(): CommonTimestampWrapper = TestCommonTimestampWrapper()
}

class TestCommonTimestampWrapper() : CommonTimestampWrapper {

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
