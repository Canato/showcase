package com.can_apps.common.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface CommonCoroutineDispatcherFactory {

    val IO: CoroutineContext

    val UI: CoroutineContext
}

class CommonCoroutineDispatcherFactoryDefault @Inject constructor() :
    CommonCoroutineDispatcherFactory {

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
