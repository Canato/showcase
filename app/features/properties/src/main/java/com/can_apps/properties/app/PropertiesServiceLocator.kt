package com.can_apps.properties.app

import android.content.Context
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryDefault
import com.can_apps.common.network.CommonHttpClientProvider
import com.can_apps.properties.bresenter.PropertiesPresenter
import com.can_apps.properties.core.PropertiesContract
import com.can_apps.properties.core.PropertiesInteractor
import com.can_apps.properties.data.PropertiesApi
import com.can_apps.properties.data.PropertiesDtoMapper
import com.can_apps.properties.data.PropertiesDtoMapperDefault
import com.can_apps.properties.data.PropertiesRepository
import retrofit2.Retrofit
import retrofit2.create

// open for integration tests
internal open class PropertiesServiceLocator(private val context: Context) {

    private val retrofit: Retrofit
        get() = CommonHttpClientProvider(context).buildGithub()

    fun getPresenter(): PropertiesContract.Presenter =
        PropertiesPresenter(getCoroutine(), getInteractor())

    private fun getInteractor(): PropertiesContract.Interactor =
        PropertiesInteractor(getRepository())

    private fun getRepository(): PropertiesContract.Repository =
        PropertiesRepository(getApi(), getDtoMapper())

    open fun getApi(): PropertiesApi = retrofit.create()

    private fun getDtoMapper(): PropertiesDtoMapper = PropertiesDtoMapperDefault()

    open fun getCoroutine(): CommonCoroutineDispatcherFactory =
        CommonCoroutineDispatcherFactoryDefault()
}
