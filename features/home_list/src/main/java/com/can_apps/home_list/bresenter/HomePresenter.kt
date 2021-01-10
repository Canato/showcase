package com.can_apps.home_list.bresenter

import android.widget.LinearLayout
import com.can_apps.home_list.core.HomeContract
import javax.inject.Inject

internal class HomePresenter @Inject constructor(
    private val interactor: HomeContract.Interactor,
    private val mapper: HomeModelMapper
) : HomeContract.Presenter {

    private var view: HomeContract.View? = null

    override fun bind(view: HomeContract.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override fun onViewCreated() {
        val model = mapper.toModel(interactor.fetchFeatures())
        view?.updateList(model.features)
    }

    override fun onItemClick(model: HomeFeatModel, layout: LinearLayout) {
        view?.navigateToDet(model, layout)
    }
}
