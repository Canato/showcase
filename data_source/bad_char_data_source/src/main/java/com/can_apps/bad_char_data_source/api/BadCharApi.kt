package com.can_apps.bad_char_data_source.api

import com.can_apps.bad_char_data_source.BadCharDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface BadCharApi {

    suspend fun getAll(): Set<BadCharDto>

    suspend fun getById(id: Long): BadCharDto

    suspend fun getByName(name: String): Set<BadCharDto>

    suspend fun getBySeason(id: Int): Set<BadCharDto>
}

// https://breakingbadapi.com
internal interface Api {
    // Pagination
    // Use a query parameter to limit the amount of characters you receive, and to offset the starting number. Consider the following request.
    // /api/characters?limit=10&offset=10
    // This request would give you an array of 10 characters, starting at index 10 (the 11th id).
    // Note: To add pagination create logic for loop when filter/search return not much answer

    @GET("/api/characters")
    suspend fun getAll(): Set<BadCharDto>

    @GET("/api/characters/{id}")
    suspend fun getById(
        @Path("id") id: Long
    ): BadCharDto

    @GET("/api/characters")
    suspend fun getNameSearch(
        @Query("name") name: String
    ): Set<BadCharDto>

    @GET("/api/season")
    suspend fun getSeasonFilter(
        @Query("season") season: Int
    ): Set<BadCharDto>
}

internal class BadCharApiDefault(private val api: Api) : BadCharApi {

    override suspend fun getAll(): Set<BadCharDto> = api.getAll()

    override suspend fun getById(id: Long): BadCharDto = api.getById(id)

    override suspend fun getByName(name: String): Set<BadCharDto> = api.getNameSearch(name)

    override suspend fun getBySeason(id: Int): Set<BadCharDto> = api.getSeasonFilter(id)
}
