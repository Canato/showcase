package com.can_apps.rank_board.bresenter

import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

internal class RankPresenter @Inject constructor(
    @Named("ui") private val uiDispatcher: CoroutineContext,
    @Named("io") private val ioDispatcher: CoroutineContext,
    private val interactor: RankContract.Interactor,
    private val modelMapper: RankModelMapper
) : RankContract.Presenter, CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = uiDispatcher + job

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

    private fun CoroutineScope.fetchData() = launch(ioDispatcher) {
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
        launch(uiDispatcher) {
            view?.hideLoading()
            view?.showError()
            view?.updateResetTime("0")
        }

    private fun CoroutineScope.updateList(model: List<RankModel>) =
        launch(uiDispatcher) {
            view?.hideLoading()
            view?.updateRankList(model)
        }

    private fun CoroutineScope.updateReset(resetTitle: String) =
        launch(uiDispatcher) {
            view?.updateResetTime(resetTitle)
        }
}
