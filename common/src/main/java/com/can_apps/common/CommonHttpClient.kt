package com.can_apps.common

interface CommonHttpClient {

    fun buildRank(): Retrofit

}

internal class Urls {
    companion object {
        const val RANK_URL = "https://www.stairwaylearning.com"
    }
}

class CommonHttpClientProvider : CommonHttpClient {

    override fun buildRank(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(Urls.RANK_URL)
            .build()
}