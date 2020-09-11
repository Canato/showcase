package com.can_apps.rank_board.bresenter

sealed class RankModel {
    abstract val username: RankUsernameModel
    abstract val weeklyXP: RankXpModel

    data class MyOwn(
        override val username: RankUsernameModel,
        override val weeklyXP: RankXpModel
    ) : RankModel()

    data class Profile(
        override val username: RankUsernameModel,
        override val weeklyXP: RankXpModel
    ) : RankModel()
}

inline class RankXpModel(val value: String)
inline class RankUsernameModel(val value: String)