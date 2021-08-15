package com.can_apps.properties.data

import com.can_apps.properties.core.IdDomain
import com.can_apps.properties.core.PriceDomain
import com.can_apps.properties.core.PropertiesDomain

internal interface PropertiesDtoMapper {

    fun toDomain(dto: Set<PropertyInfoDto>): Set<PropertiesDomain>
}

internal class PropertiesDtoMapperDefault : PropertiesDtoMapper {

    override fun toDomain(dto: Set<PropertyInfoDto>): Set<PropertiesDomain> =
        dto.map {
            PropertiesDomain(
                IdDomain(it.id.value),
                PriceDomain(it.price.value.toBigDecimal()),
            )
        }.toSet()
}
