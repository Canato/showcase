package com.can_apps.rank_board.bresenter

import com.can_apps.common.CommonCoroutineDispatcherFactory
import com.can_apps.rank_board.core.RankContract

internal class RankPresenter(
    coroutine: CommonCoroutineDispatcherFactory,
    interactor: RankContract.Interactor,
    modelMapper: RankModelMapper
) : RankContract.Presenter {

    override fun bindView(view: RankContract.View) {
        TODO("Not yet implemented")
    }

    override fun unbindView() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated() {
        TODO("Not yet implemented")
    }
}