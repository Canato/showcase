package com.can_apps.rank_board.bresenter

import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal class RankPresenter @Inject constructor(
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
        fetchData()
    }

    private fun CoroutineScope.fetchData() = launch(dispatcher.IO) {
        when (val domain = interactor.getInitialState()) {
            is RankDomain.Valid -> {
                val resetTitle = modelMapper.toResetTitle(domain.resetTime)
                updateReset(resetTitle)

                val model = modelMapper.toModel(domain.profiles)
                updateList(model)
            }
            RankDomain.Empty -> {
                updateEmptyState()
            }
        }
    }

    private fun CoroutineScope.updateEmptyState() =
        launch(dispatcher.UI) {
            view?.hideLoading()
            view?.showError()
            view?.updateResetTime("0")
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
