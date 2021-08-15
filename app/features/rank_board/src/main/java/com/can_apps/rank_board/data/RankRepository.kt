package com.can_apps.rank_board.data

import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankProfileDomain

internal class RankRepository(
    private val rankApi: RankApi,
    private val dtoMapper: RankDtoMapper
) : RankContract.Repository {

    override suspend fun getProfiles(): Set<RankProfileDomain> =
        dtoMapper.toDomain(rankApi.getAll().profiles)
}
