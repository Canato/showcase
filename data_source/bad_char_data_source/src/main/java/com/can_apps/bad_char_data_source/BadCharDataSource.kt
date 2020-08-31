package com.can_apps.bad_char_data_source

import com.can_apps.bad_char_data_source.api.BadCharApi
import com.can_apps.bad_char_data_source.api.BadCharApiDefault
import retrofit2.Retrofit
import retrofit2.create

interface BadCharDataSource {

    suspend fun getAll(): Set<BadCharDto>?

    suspend fun getSearchName(name: BadCharNameDto): Set<BadCharDto>?

    suspend fun getSeasonFilter(season: BadCharSeasonDto): Set<BadCharDto>?

    suspend fun getById(id: BadCharIdDto): BadCharDto?
}

fun badCharDataSourceProvider(retrofit: Retrofit): BadCharDataSource =
    BadCharDataSourceDefault(BadCharApiDefault(retrofit.create()))

internal class BadCharDataSourceDefault(private val api: BadCharApi) : BadCharDataSource {

    private var charDtoCache: Set<BadCharDto>? = null

    override suspend fun getAll(): Set<BadCharDto>? {
        if (charDtoCache.isNullOrEmpty()) charDtoCache = api.getAll()
        return charDtoCache
    }

    override suspend fun getSearchName(name: BadCharNameDto): Set<BadCharDto>? =
        charDtoCache?.filter { it.name.value.contains(name.value) }?.toSet()
            ?: api.getByName(name.value)

    override suspend fun getSeasonFilter(season: BadCharSeasonDto): Set<BadCharDto>? =
        charDtoCache?.filter { it.seasons.contains(season) }?.toSet()
            ?: api.getBySeason(season.value)

    override suspend fun getById(id: BadCharIdDto): BadCharDto? =
        charDtoCache?.firstOrNull { it.id == id }
            ?: api.getById(id.value)
}