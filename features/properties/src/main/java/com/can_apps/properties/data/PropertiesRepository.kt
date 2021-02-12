package com.can_apps.properties.data

import com.can_apps.average_data_source.PropertiesDataSource
import com.can_apps.properties.core.PropertiesContract
import com.can_apps.properties.core.PropertiesDomain

internal class PropertiesRepository(
    private val dataSource: PropertiesDataSource,
    private val mapper: PropertiesDtoMapper
) : PropertiesContract.Repository {

    override suspend fun getPrices(): Set<PropertiesDomain> =
        mapper.toDomain(dataSource.getPrices())
}
