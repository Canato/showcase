package com.can_apps.properties.data

import com.can_apps.average_data_source.PriceDto
import com.can_apps.properties.core.PriceDomain

internal interface PropertiesMapper {

    fun toDomain(dto: Set<PriceDto>): Set<PriceDomain>
}

internal class PropertiesMapperDefault : PropertiesMapper {

    override fun toDomain(dto: Set<PriceDto>): Set<PriceDomain> =
        dto.map { PriceDomain(it.value) }.toSet()
}