package com.can_apps.bad_char_data_source

import com.google.gson.annotations.SerializedName

data class BadCharDto(
    @SerializedName("char_id")
    val id: BadCharIdDto,
    @SerializedName("name")
    val name: BadCharNameDto,
    @SerializedName("occupation")
    val occupation: Set<BadCharOccupationDto>,
    @SerializedName("img")
    val image: BadCharImgDto,
    @SerializedName("status")
    val status: BadCharStatusDto,
    @SerializedName("nickname")
    val nickname: BadCharNicknameDto,
    @SerializedName("appearance")
    val seasons: Set<BadCharSeasonDto>
)

inline class BadCharIdDto(val value: Long)
inline class BadCharNameDto(val value: String)
inline class BadCharOccupationDto(val value: String)
inline class BadCharImgDto(val value: String)
inline class BadCharStatusDto(val value: String)
inline class BadCharNicknameDto(val value: String)
inline class BadCharSeasonDto(val value: Int)