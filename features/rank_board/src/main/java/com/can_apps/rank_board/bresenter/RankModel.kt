package com.can_apps.rank_board.bresenter

import com.can_apps.rank_board.core.RankUsernameDomain
import com.can_apps.rank_board.core.RankXpDomain

internal sealed class RankModel {

    data class MyOwn(
        val username: RankUsernameDomain,
        val weeklyXP: RankXpDomain
    ) : RankModel()

    data class Profile(
        val username: RankUsernameDomain,
        val weeklyXP: RankXpDomain
    ) : RankModel()
}