package com.can_apps.properties.data

import com.can_apps.average_data_source.PropertiesDto
import com.can_apps.properties.core.IdDomain
import com.can_apps.properties.core.PriceDomain
import com.can_apps.properties.core.PropertiesDomain

internal interface PropertiesDtoMapper {

    fun toDomain(dto: PropertiesDto): Set<PropertiesDomain>
}

internal class PropertiesDtoMapperDefault : PropertiesDtoMapper {

    override fun toDomain(dto: PropertiesDto): Set<PropertiesDomain> =
        dto.properties
            .map {
                PropertiesDomain(
                    IdDomain(it.id.value),
                    PriceDomain(it.price.value.toBigDecimal())
                )
            }
            .toSet()
}
