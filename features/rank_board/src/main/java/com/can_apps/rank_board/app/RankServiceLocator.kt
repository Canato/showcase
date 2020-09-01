package com.can_apps.rank_board.app

import android.content.Context
import com.can_apps.common.CommonCalendarWrapper
import com.can_apps.common.CommonCalendarWrapperDefault
import com.can_apps.common.CommonCoroutineDispatcherFactory
import com.can_apps.common.CommonCoroutineDispatcherFactoryDefault
import com.can_apps.common.CommonHttpClientProvider
import com.can_apps.common.CommonStringResource
import com.can_apps.rank_board.bresenter.RankModelMapper
import com.can_apps.rank_board.bresenter.RankModelMapperDefault
import com.can_apps.rank_board.bresenter.RankPresenter
import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankInteractor
import com.can_apps.rank_board.data.RankDtoMapper
import com.can_apps.rank_board.data.RankDtoMapperDefault
import com.can_apps.rank_board.data.RankRepository
import com.can_apps.rank_data_source.RankDataSource
import com.can_apps.rank_data_source.getRankDataSourceProvider
import retrofit2.Retrofit
import retrofit2.create

// open for integration tests
internal open class RankServiceLocator(private val context: Context) {

    private val retrofit: Retrofit
        get() = CommonHttpClientProvider().buildRank()

    fun getPresenter(): RankContract.Presenter =
        RankPresenter(
            getCoroutine(),
            getInteractor(),
            getModelMapper()
        )

    private fun getInteractor(): RankContract.Interactor =
        RankInteractor(
            getRepository(),
            getCalendar(),
            getCoroutine()
        )

    private fun getRepository(): RankContract.Repository =
        RankRepository(
            getDataSource(),
            getDtoMapper()
        )

    private fun getModelMapper(): RankModelMapper =
        RankModelMapperDefault(getString())

    open fun getString(): CommonStringResource =
        CommonStringResource(context)

    open fun getCoroutine(): CommonCoroutineDispatcherFactory =
        CommonCoroutineDispatcherFactoryDefault()

    open fun getCalendar(): CommonCalendarWrapper =
        CommonCalendarWrapperDefault()

    open fun getDataSource(): RankDataSource =
        getRankDataSourceProvider(retrofit.create())

    private fun getDtoMapper(): RankDtoMapper =
        RankDtoMapperDefault()

    fun getAdapter(): RankAdapter = RankAdapter()
}