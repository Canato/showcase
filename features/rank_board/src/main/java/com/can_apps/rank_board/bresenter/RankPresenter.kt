package com.can_apps.rank_board.bresenter

import com.can_apps.common.CommonCoroutineDispatcherFactory
import com.can_apps.rank_board.core.RankContract
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class RankPresenter(
    private val dispatcher: CommonCoroutineDispatcherFactory,
    private val interactor: RankContract.Interactor,
    private val modelMapper: RankModelMapper
) : RankContract.Presenter, CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = dispatcher.UI + job

    private var view: RankContract.View? = null

    override fun bindView(view: RankContract.View) {
        this.view = view
    }

    override fun unbindView() {
        view = null
        job.cancel()
    }

    override fun onViewCreated() {
        view?.showLoading()
        fecthData()
    }

    private fun CoroutineScope.fecthData() = launch(dispatcher.IO) {
        val domain = interactor.getInitialState()

        val resetTitle = modelMapper.toResetTitle(domain.resetTime)
        updateReset(resetTitle)

        val model = modelMapper.toModel(domain.profiles)
        updateList(model)
    }

    private fun CoroutineScope.updateList(model: List<RankModel>) =
        launch(dispatcher.UI) {
            view?.hideLoading()
            view?.updateRankList(model)
        }

    private fun CoroutineScope.updateReset(resetTitle: String) =
        launch(dispatcher.UI) {
            view?.updateResetTime(resetTitle)
        }
}
