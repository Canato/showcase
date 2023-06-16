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

@JvmInline
value class IdDto(val value: Int)
@JvmInline
value class PriceDto(val value: Float)
