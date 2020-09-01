package com.can_apps.rank_board.core

import com.can_apps.common.CommonCalendarWrapper
import com.can_apps.common.CommonCoroutineDispatcherFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

internal class RankInteractor(
    private val repository: RankContract.Repository,
    private val calendarWrapper: CommonCalendarWrapper,
    private val dispatcher: CommonCoroutineDispatcherFactory
) : RankContract.Interactor, CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = dispatcher.IO + job

    override suspend fun getInitialState(): RankDomain {
//        val profiles = coroutineScope { async(dispatcher.IO) { repository.getProfiles() } }
        val profiles = repository.getProfiles()
        val dayOfWeek = calendarWrapper.getDayOfWeek()

        val resetTime = RankResetTimeDomain(8 - dayOfWeek)
        return RankDomain(profiles, resetTime)
    }
}