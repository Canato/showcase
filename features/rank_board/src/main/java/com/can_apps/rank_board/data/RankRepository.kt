package com.can_apps.rank_board.data

import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankProfileDomain

internal class RankRepository : RankContract.Repository {

    override suspend fun getProfiles(): Set<RankProfileDomain> {
        TODO("Not yet implemented")
    }
}