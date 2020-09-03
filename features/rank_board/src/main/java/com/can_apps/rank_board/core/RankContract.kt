package com.can_apps.rank_board.core

import com.can_apps.rank_board.bresenter.RankModel

internal interface RankContract {

    interface View {

        fun updateResetTime(resetTime: String)

        fun updateRankList(model: List<RankModel>)

        fun showLoading()

        fun hideLoading()

        fun showError()
    }

    interface Presenter {

        fun bindView(view: View)

        fun unbindView()

        fun onViewCreated()
    }

    interface Interactor {

        suspend fun getInitialState(): RankDomain
    }

    interface Repository {

        suspend fun getProfiles(): Set<RankProfileDomain>
    }
}
