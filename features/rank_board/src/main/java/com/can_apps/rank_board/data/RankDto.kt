package com.can_apps.rank_board.data

import com.google.gson.annotations.SerializedName

internal data class RankDto(
    @SerializedName("profiles")
    val profiles: Set<RankProfileDto>
)

internal data class RankProfileDto(
    @SerializedName("username")
    val username: RankUsernameDto,
    @SerializedName("weeklyXP")
    val weeklyXP: RankXpDto,
    @SerializedName("isCurrentUser")
    val isCurrentUser: RankCurrentUserDto
)

internal inline class RankUsernameDto(val value: String)
internal inline class RankXpDto(val value: Long)
internal inline class RankCurrentUserDto(val value: Boolean)
