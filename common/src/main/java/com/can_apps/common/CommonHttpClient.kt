package com.can_apps.common

import retrofit2.Retrofit

interface CommonHttpClient {

    fun buildRank(): Retrofit

    fun buildBad(): Retrofit
}

internal class Urls {
    companion object {
        const val RANK_URL = "https://www.stairwaylearning.com"
        const val BAD_URL = "https://breakingbadapi.com"
    }
}

class CommonHttpClientProvider : CommonHttpClient {

    override fun buildRank(): Retrofit = Retrofit.Builder().baseUrl(Urls.RANK_URL).build()

    override fun buildBad(): Retrofit = Retrofit.Builder().baseUrl(Urls.BAD_URL).build()
}
