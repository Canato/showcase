package com.can_apps.properties.data

import retrofit2.http.GET

// https://raw.githubusercontent.com/rightmove/Code-Challenge-Android/master/properties.json
internal interface PropertiesApi {

    @GET("/rightmove/Code-Challenge-Android/master/properties.json")
    suspend fun getProperties(): PropertiesDto
}
