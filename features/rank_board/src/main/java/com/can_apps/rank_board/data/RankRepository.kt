package com.can_apps.rank_board.data

import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankProfileDomain
import com.can_apps.rank_data_source.RankDataSource
import javax.inject.Inject
import javax.inject.Named

internal class RankRepository @Inject constructor(
    private val dataSource: RankDataSource,
    private val dtoMapper: RankDtoMapper
) : RankContract.Repository {

    override suspend fun getProfiles(): Set<RankProfileDomain> =
        dtoMapper.toDomain(dataSource.getAll().profiles)
}
