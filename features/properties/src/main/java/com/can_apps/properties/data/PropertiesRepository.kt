package com.can_apps.properties.data

import com.can_apps.average_data_source.PropertiesDataSource
import com.can_apps.properties.core.PriceDomain
import com.can_apps.properties.core.PropertiesContract

internal class PropertiesRepository(
    private val dataSource: PropertiesDataSource,
    private val mapper: PropertiesDtoMapper
) : PropertiesContract.Repository {

    override suspend fun getPrices(): Set<PriceDomain> =
        mapper.toDomain(dataSource.getPrices())
}