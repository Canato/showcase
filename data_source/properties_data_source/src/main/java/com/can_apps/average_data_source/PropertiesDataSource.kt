package com.can_apps.average_data_source

import com.can_apps.average_data_source.api.Api
import com.can_apps.average_data_source.api.PropertiesApi
import com.can_apps.average_data_source.api.PropertiesApiDefault

interface PropertiesDataSource {

    suspend fun getPrices(): Set<PriceDto>
}

fun getPropertiesDataSourceProvider(api: Api): PropertiesDataSource =
    PropertiesDataSourceDefault(PropertiesApiDefault(api))

internal class PropertiesDataSourceDefault(private val api: PropertiesApi) : PropertiesDataSource {

    override suspend fun getPrices(): Set<PriceDto> = try {
        val response = api.getProperties()
        response.properties.map { it.price }.toSet()
    } catch (e: Exception) {
        // todo #21 log Exception
        emptySet()
    }
}
