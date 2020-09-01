package com.can_apps.rank_board.bresenter

import com.can_apps.rank_board.core.RankUsernameDomain

internal sealed class RankModel {
    abstract val username: RankUsernameDomain
    abstract val weeklyXP: RankXpModel

    data class MyOwn(
        override val username: RankUsernameDomain,
        override val weeklyXP: RankXpModel
    ) : RankModel()

    data class Profile(
        override val username: RankUsernameDomain,
        override val weeklyXP: RankXpModel
    ) : RankModel()
}

inline class RankXpModel(val value: String)
