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

@JvmInline
internal value class RankUsernameDomain(val value: String)
@JvmInline
internal value class RankXpDomain(val value: Long)
@JvmInline
internal value class RankCurrentUserDomain(val value: Boolean)
@JvmInline
internal value class RankResetTimeDomain(val value: Int)
