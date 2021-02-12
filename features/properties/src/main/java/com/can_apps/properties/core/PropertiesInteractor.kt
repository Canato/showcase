package com.can_apps.properties.core

import com.can_apps.common.extensions.averageBigDecimal

internal class PropertiesInteractor(
    private val repository: PropertiesContract.Repository
) : PropertiesContract.Interactor {

    override suspend fun getAverage(): PriceDomain? =
        repository.getPrices().run {
            if (this.isEmpty()) null
            else PriceDomain(this.map { it.price.value }.averageBigDecimal())
        }
}
