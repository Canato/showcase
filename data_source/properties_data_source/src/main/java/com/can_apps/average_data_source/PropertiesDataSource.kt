package com.can_apps.average_data_source

import com.can_apps.average_data_source.api.Api
import com.can_apps.average_data_source.api.PropertiesApi
import com.can_apps.average_data_source.api.PropertiesApiDefault

interface PropertiesDataSource {

    suspend fun getPrices(): PropertiesDto
}

fun getPropertiesDataSourceProvider(api: Api): PropertiesDataSource =
    PropertiesDataSourceDefault(PropertiesApiDefault(api))

internal class PropertiesDataSourceDefault(private val api: PropertiesApi) : PropertiesDataSource {

    override suspend fun getPrices(): PropertiesDto = try {
        api.getProperties()
    } catch (e: Exception) {
        // todo #21 log Exception
        PropertiesDto(emptySet())
    }
}
