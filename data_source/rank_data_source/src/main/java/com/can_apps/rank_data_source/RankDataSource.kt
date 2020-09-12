package com.can_apps.rank_data_source

import com.can_apps.rank_data_source.api.Api
import com.can_apps.rank_data_source.api.RankApi
import com.can_apps.rank_data_source.api.RankApiDefault

interface RankDataSource {

    suspend fun getAll(): RankDto
}

fun getRankDataSourceProvider(api: Api): RankDataSource =
    RankDataSourceDefault(RankApiDefault(api))

internal class RankDataSourceDefault(private val api: RankApi) : RankDataSource {

    override suspend fun getAll(): RankDto = try {
        api.getAll()
    } catch (e: Exception) {
        // todo #21 log Exception
        RankDto(emptySet())
    }
}
