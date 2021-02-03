package com.can_apps.properties

import com.can_apps.average_data_source.PriceDto
import com.can_apps.average_data_source.PropertiesDataSource
import com.can_apps.average_data_source.api.Api
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryUnconfined
import com.can_apps.properties.app.PropertiesServiceLocator
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

internal class MockPropertiesServiceLocator(
    private val mockServerUrl: String
) : PropertiesServiceLocator(mockk(relaxed = true)) {

    override fun getCoroutine(): CommonCoroutineDispatcherFactory =
        CommonCoroutineDispatcherFactoryUnconfined()

    override fun getDataSource(): PropertiesDataSource =
        TestPropertiesDataSource(
            Retrofit
                .Builder()
                .baseUrl(mockServerUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        )
}

private class TestPropertiesDataSource(
    private val api: Api
) : PropertiesDataSource {

    override suspend fun getPrices(): Set<PriceDto> = runBlocking {
        api.getProperties().properties.map { it.price }.toSet()
    }
}
