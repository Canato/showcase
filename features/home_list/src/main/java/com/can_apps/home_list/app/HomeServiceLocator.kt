package com.can_apps.home_list.app

import android.content.Context
import com.can_apps.common.wrappers.CommonStringResource
import com.can_apps.home_list.bresenter.HomeModelMapper
import com.can_apps.home_list.bresenter.HomeModelMapperDefault
import com.can_apps.home_list.bresenter.HomePresenter
import com.can_apps.home_list.core.HomeContract
import com.can_apps.home_list.core.HomeInteractor

internal class HomeServiceLocator(private val context: Context) {

    private val presenter = HomePresenter(getInteractor(), getMapper())

    fun getPresenter(): HomeContract.Presenter = presenter

    private fun getMapper(): HomeModelMapper =
        HomeModelMapperDefault(getStringResource())

    private fun getStringResource(): CommonStringResource =
        CommonStringResource(context)

    private fun getInteractor(): HomeContract.Interactor =
        HomeInteractor()

    fun getAdapter(): HomeAdapter = HomeAdapter(presenter)
}
