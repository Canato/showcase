package com.can_apps.properties

import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryUnconfined
import com.can_apps.properties.app.PropertiesServiceLocator
import com.can_apps.properties.data.PropertiesApi
import com.can_apps.properties.data.PropertiesDto
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

    override fun getApi(): PropertiesApi =
        TestPropertiesApi(
            Retrofit
                .Builder()
                .baseUrl(mockServerUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        )
}

private class TestPropertiesApi(
    private val api: PropertiesApi
) : PropertiesApi {

    override suspend fun getProperties(): PropertiesDto = runBlocking {
        api.getProperties()
    }
}
