package com.can_apps.rank_board.core

import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.wrappers.CommonCalendarWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

internal class RankInteractor @Inject constructor(
    private val repository: RankContract.Repository,
    private val calendarWrapper: CommonCalendarWrapper,
    @Named("io") private val ioDispatcher: CoroutineContext
) : RankContract.Interactor, CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = ioDispatcher + job

    override suspend fun getInitialState(): RankDomain {
        val profiles = coroutineScope { async(ioDispatcher) { repository.getProfiles() } }
        val dayOfWeek = calendarWrapper.getDayOfWeek()

        val resetTime = RankResetTimeDomain(9 - dayOfWeek) // Resets on Mondays

        return if (profiles.await().isEmpty()) RankDomain.Empty
        else RankDomain.Valid(profiles.await(), resetTime)
    }
}
