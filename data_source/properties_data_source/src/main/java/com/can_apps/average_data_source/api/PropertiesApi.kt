package com.can_apps.average_data_source.api

import com.can_apps.average_data_source.PropertiesDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

internal interface PropertiesApi {

    suspend fun getProperties(): PropertiesDto
}

// https://raw.githubusercontent.com/rightmove/Code-Challenge-Android/master/properties.json
interface Api {

    @GET("/rightmove/Code-Challenge-Android/master/properties.json")
    suspend fun getProperties(): PropertiesDto
}

internal class PropertiesApiDefault(private val api: Api) : PropertiesApi {

    override suspend fun getProperties(): PropertiesDto = api.getProperties()
}