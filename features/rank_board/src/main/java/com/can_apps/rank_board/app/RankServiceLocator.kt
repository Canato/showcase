package com.can_apps.rank_board.app

import android.content.Context
import com.can_apps.common.CommonCoroutineDispatcherFactory
import com.can_apps.common.CommonCoroutineDispatcherFactoryDefault
import com.can_apps.common.CommonStringResource
import com.can_apps.rank_board.bresenter.RankModelMapper
import com.can_apps.rank_board.bresenter.RankModelMapperDefault
import com.can_apps.rank_board.bresenter.RankPresenter
import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankInteractor

// open for integration tests
internal open class RankServiceLocator(private val context: Context) {

    fun getPresenter(): RankContract.Presenter =
        RankPresenter(
            getCoroutine(),
            getInteractor(),
            getModelMapper()
        )

    private fun getModelMapper(): RankModelMapper =
        RankModelMapperDefault(getString())

    open fun getString(): CommonStringResource =
        CommonStringResource(context)

    open fun getCoroutine(): CommonCoroutineDispatcherFactory =
        CommonCoroutineDispatcherFactoryDefault()

    private fun getInteractor(): RankContract.Interactor =
        RankInteractor()
}