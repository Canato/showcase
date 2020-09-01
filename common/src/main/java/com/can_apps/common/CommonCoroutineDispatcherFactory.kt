package com.can_apps.common

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface CommonCoroutineDispatcherFactory {

    val io: CoroutineContext

    val ui: CoroutineContext
}

class CommonCoroutineDispatcherFactoryDefault : CommonCoroutineDispatcherFactory {
    override val io: CoroutineContext
        get() = Dispatchers.IO

    override val ui: CoroutineContext
        get() = Dispatchers.Main
}

@ExperimentalCoroutinesApi
class CommonCoroutineDispatcherFactoryUnconfined : CommonCoroutineDispatcherFactory {

    override val io: CoroutineContext
        get() = Unconfined

    override val ui: CoroutineContext
        get() = Unconfined
}
