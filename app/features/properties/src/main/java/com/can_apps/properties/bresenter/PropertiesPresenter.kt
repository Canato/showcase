package com.can_apps.properties.bresenter

import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.properties.core.PropertiesContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

internal class PropertiesPresenter(
    private val dispatcher: CommonCoroutineDispatcherFactory,
    private val interactor: PropertiesContract.Interactor,
) : PropertiesContract.Presenter, CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = dispatcher.UI + job

    private var view: PropertiesContract.View? = null

    override fun bindView(view: PropertiesContract.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override fun onViewCreated() {
        view?.showLoading()
        fetchAverageData()
    }

    private fun CoroutineScope.fetchAverageData() = launch(dispatcher.IO) {
        try {
            val average = interactor.getAverage()
            if (average == null) showError()
            else updateAverage(average.value.toString())
        } catch (e: Exception) {
            // todo #21 Log
            showError()
        }
    }

    private fun CoroutineScope.updateAverage(average: String) = launch(dispatcher.UI) {
        view?.hideLoading()
        view?.updatePriceAverage(average)
    }

    private fun CoroutineScope.showError() = launch(dispatcher.UI) {
        view?.hideLoading()
        view?.showError()
    }
}
