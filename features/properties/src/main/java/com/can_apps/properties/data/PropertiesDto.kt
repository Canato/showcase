package com.can_apps.properties.data

import com.google.gson.annotations.SerializedName

data class PropertiesDto(
    @SerializedName("properties")
    val properties: Set<PropertyInfoDto>
)

data class PropertyInfoDto(
    @SerializedName("id")
    val id: IdDto,
    @SerializedName("price")
    val price: PriceDto,
)

inline class IdDto(val value: Int)
inline class PriceDto(val value: Float)
