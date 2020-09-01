package com.can_apps.rank_board.bresenter

import com.can_apps.common.CommonStringResource
import com.can_apps.rank_board.R
import com.can_apps.rank_board.core.RankProfileDomain
import com.can_apps.rank_board.core.RankResetTimeDomain

internal interface RankModelMapper {

    fun toResetTitle(resetTime: RankResetTimeDomain): String

    fun toModel(profiles: Set<RankProfileDomain>): List<RankModel>
}

internal class RankModelMapperDefault(private val string: CommonStringResource) : RankModelMapper {

    override fun toResetTitle(resetTime: RankResetTimeDomain): String =
        when (resetTime.value) {
            1 -> string.getString(R.string.tomorrow)
            in 2..7 -> "${resetTime.value} ${string.getString(R.string.days)}"
            else -> "Never XoXo"
        }

    override fun toModel(profiles: Set<RankProfileDomain>): List<RankModel> =
        profiles
            .sortedBy { it.weeklyXP.value }
            .map {
                if (it.isCurrentUser.value) RankModel.MyOwn(it.username, it.weeklyXP)
                else RankModel.Profile(it.username, it.weeklyXP)
            }
}