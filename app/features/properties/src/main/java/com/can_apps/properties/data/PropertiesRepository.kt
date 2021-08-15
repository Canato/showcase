package com.can_apps.properties.data

import com.can_apps.properties.core.PropertiesContract
import com.can_apps.properties.core.PropertiesDomain

internal class PropertiesRepository(
    private val api: PropertiesApi,
    private val mapper: PropertiesDtoMapper
) : PropertiesContract.Repository {

    override suspend fun getProperties(): Set<PropertiesDomain> =
        mapper.toDomain(api.getProperties().properties)
}
