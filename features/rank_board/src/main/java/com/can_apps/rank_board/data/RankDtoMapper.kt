package com.can_apps.rank_board.data

import com.can_apps.rank_board.core.RankProfileDomain
import com.can_apps.rank_data_source.RankProfileDto

internal interface RankDtoMapper {

    fun toDomain(profiles: Set<RankProfileDto>): Set<RankProfileDomain>
}

internal class RankDtoMapperDefault : RankDtoMapper {

    override fun toDomain(profiles: Set<RankProfileDto>): Set<RankProfileDomain> {
        TODO("Not yet implemented")
    }
}