package com.can_apps.chat

import com.can_apps.chat.app.ChatServiceLocator
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

internal class MockChatServiceLocator(
    private val testDispatcher: TestCoroutineDispatcher,
    private val debounceWaitValue: Long
) : ChatServiceLocator() {

    override fun getDispatcher(): CommonCoroutineDispatcherFactory =
        TestCoroutineDispatcherFactory(testDispatcher)

    override fun getDebounceWait(): Long = debounceWaitValue
}

class TestCoroutineDispatcherFactory(
    private val testCoroutineDispatcher: TestCoroutineDispatcher
) : CommonCoroutineDispatcherFactory {

    override val IO: CoroutineContext
        get() = testCoroutineDispatcher
    override val UI: CoroutineContext
        get() = testCoroutineDispatcher
}
