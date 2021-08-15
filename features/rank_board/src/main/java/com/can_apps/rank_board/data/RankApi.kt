package com.can_apps.rank_board.data

import retrofit2.http.GET

// https://www.stairwaylearning.com/api/v1/stub/leaderboard
internal interface RankApi {

    @GET("/api/v1/stub/leaderboard")
    suspend fun getAll(): RankDto
}
