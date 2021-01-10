package com.can_apps.rank_board.bresenter

import com.can_apps.common.wrappers.CommonStringResource
import com.can_apps.rank_board.R
import com.can_apps.rank_board.core.RankProfileDomain
import com.can_apps.rank_board.core.RankResetTimeDomain
import javax.inject.Inject

internal interface RankModelMapper {

    fun toResetTitle(resetTime: RankResetTimeDomain): String

    fun toModel(profiles: Set<RankProfileDomain>): List<RankModel>
}

internal class RankModelMapperDefault @Inject constructor(
    private val string: CommonStringResource
) : RankModelMapper {

    override fun toResetTitle(resetTime: RankResetTimeDomain): String =
        when (resetTime.value) {
            8 -> string.getString(R.string.tomorrow)
            in 2..7 -> "${resetTime.value} ${string.getString(R.string.days)}"
            else -> "Never XoXo"
        }

    override fun toModel(profiles: Set<RankProfileDomain>): List<RankModel> =
        profiles
            .sortedByDescending { it.weeklyXP.value }
            .map {
                if (it.isCurrentUser.value) RankModel.MyOwn(
                    RankUsernameModel(it.username.value),
                    RankXpModel("${it.weeklyXP.value} XP")
                )
                else RankModel.Profile(
                    RankUsernameModel(it.username.value),
                    RankXpModel("${it.weeklyXP.value} XP")
                )
            }
}
