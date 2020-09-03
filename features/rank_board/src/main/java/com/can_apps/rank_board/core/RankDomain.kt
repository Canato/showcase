package com.can_apps.rank_board.core

internal sealed class RankDomain {

    data class Valid(
        val profiles: Set<RankProfileDomain>,
        val resetTime: RankResetTimeDomain
    ) : RankDomain()

    object Empty : RankDomain()
}

internal data class RankProfileDomain(
    val username: RankUsernameDomain,
    val weeklyXP: RankXpDomain,
    val isCurrentUser: RankCurrentUserDomain
)

internal inline class RankUsernameDomain(val value: String)
internal inline class RankXpDomain(val value: Long)
internal inline class RankCurrentUserDomain(val value: Boolean)
internal inline class RankResetTimeDomain(val value: Int)
