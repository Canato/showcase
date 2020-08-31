package com.can_apps.rank_data_source

import com.google.gson.annotations.SerializedName

data class RankDto(
    @SerializedName("profiles")
    val profiles: Set<RankProfileDto>
)

data class RankProfileDto(
    @SerializedName("username")
    val username: RankUsernameDto,
    @SerializedName("weeklyXP")
    val weeklyXP: RankXpDto,
    @SerializedName("isCurrentUser")
    val isCurrentUser: RankCurrentUserDto
)

inline class RankUsernameDto(val value: String)
inline class RankXpDto(val value: Long)
inline class RankCurrentUserDto(val value: Boolean)
