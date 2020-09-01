package com.can_apps.rank_board.core

import com.can_apps.common.CommonCalendarWrapper
import com.can_apps.common.CommonCoroutineDispatcherFactory
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class RankInteractor(
    private val repository: RankContract.Repository,
    private val calendarWrapper: CommonCalendarWrapper,
    private val dispatcher: CommonCoroutineDispatcherFactory
) : RankContract.Interactor, CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = dispatcher.IO + job

    override suspend fun getInitialState(): RankDomain {
        val profiles = coroutineScope { async(dispatcher.IO) { repository.getProfiles() } }
        val dayOfWeek = calendarWrapper.getDayOfWeek()

        val resetTime = RankResetTimeDomain(9 - dayOfWeek) // Resets on Mondays
        return RankDomain(profiles.await(), resetTime)
    }
}
