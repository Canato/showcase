package com.can_apps.properties.core

internal interface PropertiesContract {

    interface View {
        fun updatePriceAverage(priceAverage: String)
        fun showError()
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindView(view: View)
        fun unbind()
        fun onViewCreated()
    }

    interface Interactor {
        suspend fun getAverage(): PriceDomain?
    }

    interface Repository {
        suspend fun getProperties(): Set<PropertiesDomain>
    }
}
