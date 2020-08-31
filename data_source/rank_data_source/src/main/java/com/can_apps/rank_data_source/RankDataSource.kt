package com.can_apps.rank_data_source

import com.can_apps.rank_data_source.api.RankApi
import com.can_apps.rank_data_source.api.RankApiDefault
import retrofit2.Retrofit
import retrofit2.create

interface RankDataSource {

    suspend fun getAll(): RankDto
}

fun getRankDataSourceProvider(retrofit: Retrofit): RankDataSource =
    RankDataSourceDefault(RankApiDefault(retrofit.create()))

internal class RankDataSourceDefault(private val api: RankApi) : RankDataSource {

    override suspend fun getAll(): RankDto = try {
        api.getAll()
    } catch (e: Exception) {
        // todo log Exception
        RankDto(emptySet())
    }
}