package com.can_apps.rank_board.data

import com.can_apps.rank_board.core.RankCurrentUserDomain
import com.can_apps.rank_board.core.RankProfileDomain
import com.can_apps.rank_board.core.RankUsernameDomain
import com.can_apps.rank_board.core.RankXpDomain

internal interface RankDtoMapper {

    fun toDomain(profiles: Set<RankProfileDto>): Set<RankProfileDomain>
}

internal class RankDtoMapperDefault : RankDtoMapper {

    override fun toDomain(profiles: Set<RankProfileDto>): Set<RankProfileDomain> =
        profiles
            .asSequence()
            .map {
                RankProfileDomain(
                    RankUsernameDomain(it.username.value),
                    RankXpDomain(it.weeklyXP.value),
                    RankCurrentUserDomain(it.isCurrentUser.value)
                )
            }.toSet()
}
