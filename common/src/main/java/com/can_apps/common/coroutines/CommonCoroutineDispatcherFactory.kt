package com.can_apps.common.coroutines

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface CommonCoroutineDispatcherFactory {

    val IO: CoroutineContext

    val UI: CoroutineContext
}

class CommonCoroutineDispatcherFactoryDefault : CommonCoroutineDispatcherFactory {
    override val IO: CoroutineContext
        get() = Dispatchers.IO

    override val UI: CoroutineContext
        get() = Dispatchers.Main
}

@ExperimentalCoroutinesApi
class CommonCoroutineDispatcherFactoryUnconfined : CommonCoroutineDispatcherFactory {

    override val IO: CoroutineContext
        get() = Unconfined

    override val UI: CoroutineContext
        get() = Unconfined
}
