package com.can_apps.rank_data_source.api

import com.can_apps.rank_data_source.RankDto
import retrofit2.http.GET

internal interface RankApi {

    suspend fun getAll(): RankDto
}

// https://www.stairwaylearning.com/api/v1/stub/leaderboard
internal interface Api {

    @GET("/api/v1/stub/leaderboard")
    suspend fun getAll(): RankDto
}

internal class RankApiDefault(private val api: Api) : RankApi {

    override suspend fun getAll(): RankDto = api.getAll()
}