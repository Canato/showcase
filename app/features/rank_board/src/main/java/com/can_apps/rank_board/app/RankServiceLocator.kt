package com.can_apps.rank_board.app

import android.content.Context
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryDefault
import com.can_apps.common.network.CommonHttpClientProvider
import com.can_apps.common.wrappers.CommonCalendarWrapper
import com.can_apps.common.wrappers.CommonCalendarWrapperDefault
import com.can_apps.common.wrappers.CommonStringResource
import com.can_apps.rank_board.bresenter.RankModelMapper
import com.can_apps.rank_board.bresenter.RankModelMapperDefault
import com.can_apps.rank_board.bresenter.RankPresenter
import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankInteractor
import com.can_apps.rank_board.data.RankApi
import com.can_apps.rank_board.data.RankDtoMapper
import com.can_apps.rank_board.data.RankDtoMapperDefault
import com.can_apps.rank_board.data.RankRepository
import retrofit2.Retrofit
import retrofit2.create

// open for integration tests
internal open class RankServiceLocator(private val context: Context) {

    private val retrofit: Retrofit
        get() = CommonHttpClientProvider(context).buildRank()

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
            rankApi = getRankApi(),
            dtoMapper = getDtoMapper()
        )

    open fun getRankApi(): RankApi = retrofit.create()

    private fun getModelMapper(): RankModelMapper =
        RankModelMapperDefault(getString())

    open fun getString(): CommonStringResource =
        CommonStringResource(context)

    open fun getCoroutine(): CommonCoroutineDispatcherFactory =
        CommonCoroutineDispatcherFactoryDefault()

    open fun getCalendar(): CommonCalendarWrapper =
        CommonCalendarWrapperDefault()

    private fun getDtoMapper(): RankDtoMapper =
        RankDtoMapperDefault()

    fun getAdapter(): RankAdapter = RankAdapter()
}
