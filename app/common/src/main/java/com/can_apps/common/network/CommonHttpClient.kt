package com.can_apps.common.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface CommonHttpClient {

    fun buildRank(): Retrofit
    fun buildBad(): Retrofit
    fun buildGithub(): Retrofit
}

internal class Urls {
    companion object {

        const val RANK_URL = "https://www.stairwaylearning.com"
        const val BAD_URL = "https://breakingbadapi.com"
        const val GITHUB_URL = "https://raw.githubusercontent.com"
    }
}

class CommonHttpClientProvider(private val context: Context) : CommonHttpClient {

    override fun buildRank(): Retrofit = buildDefault(Urls.RANK_URL)
    override fun buildBad(): Retrofit = buildDefault(Urls.BAD_URL)
    override fun buildGithub(): Retrofit = buildDefault(Urls.GITHUB_URL)

    private fun buildDefault(url: String) =
        Retrofit
            .Builder()
            .client(getOkHttpClient())
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun getOkHttpClient(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(ChuckerInterceptor(context))
            .build()
}
