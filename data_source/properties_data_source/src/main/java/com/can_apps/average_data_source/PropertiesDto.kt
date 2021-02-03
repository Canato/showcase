package com.can_apps.average_data_source

import com.google.gson.annotations.SerializedName

data class PropertiesDto(
    @SerializedName("properties")
    val properties: Set<PropertyInfoDto>
)

data class PropertyInfoDto(
    @SerializedName("price")
    val price: PriceDto,
)

inline class PriceDto(val value: Float)