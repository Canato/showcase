package com.can_apps.rank_board

import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryUnconfined
import com.can_apps.common.wrappers.CommonCalendarWrapper
import com.can_apps.common.wrappers.CommonStringResource
import com.can_apps.rank_board.bresenter.RankModelMapperDefault
import com.can_apps.rank_board.bresenter.RankPresenter
import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankInteractor
import com.can_apps.rank_board.data.RankRepository
import com.can_apps.rank_data_source.RankDataSource
import com.can_apps.rank_data_source.RankDto
import com.can_apps.rank_data_source.api.Api
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private class TestRankDataSource(
    private val api: Api
) : RankDataSource {

    override suspend fun getAll(): RankDto = runBlocking {
        api.getAll()
    }
}

internal object RankPresenterProvider {
    fun getPresenter(mockServerUrl: String, string: CommonStringResource,
                     calendarWrapper: CommonCalendarWrapper) : RankContract.Presenter {
        val dispatcher = getDispatcher()

        return RankPresenter(
            dispatcher = dispatcher,
            interactor = RankInteractor(
                repository = RankRepository(
                    dataSource = getDataSource(mockServerUrl),
                    dtoMapper = mockk()
                ),
                calendarWrapper = calendarWrapper,
                dispatcher = dispatcher
            ),
            modelMapper = RankModelMapperDefault(
                string
            )
        )
    }

    private fun getDispatcher(): CommonCoroutineDispatcherFactory =
        CommonCoroutineDispatcherFactoryUnconfined()

    private fun getDataSource(mockServerUrl: String): RankDataSource =
        TestRankDataSource(
            Retrofit
                .Builder()
                .baseUrl(mockServerUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        )
}
