package com.can_apps.rank_board

import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryUnconfined
import com.can_apps.common.wrappers.CommonCalendarWrapper
import com.can_apps.common.wrappers.CommonStringResource
import com.can_apps.rank_board.app.RankServiceLocator
import com.can_apps.rank_board.data.RankApi
import com.can_apps.rank_board.data.RankDto
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

internal class MockRankServiceLocator(
    private val mockServerUrl: String,
    private val string: CommonStringResource,
    private val calendarWrapper: CommonCalendarWrapper
) : RankServiceLocator(mockk(relaxed = true)) {

    override fun getCoroutine(): CommonCoroutineDispatcherFactory =
        CommonCoroutineDispatcherFactoryUnconfined()

    override fun getString(): CommonStringResource = string

    override fun getCalendar(): CommonCalendarWrapper = calendarWrapper

    override fun getRankApi(): RankApi =
        TestRankApi(
            Retrofit
                .Builder()
                .baseUrl(mockServerUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        )
}

private class TestRankApi(
    private val api: RankApi
) : RankApi {

    override suspend fun getAll(): RankDto = runBlocking {
        api.getAll()
    }
}