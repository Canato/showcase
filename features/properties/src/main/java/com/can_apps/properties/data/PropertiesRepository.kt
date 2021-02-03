package com.can_apps.properties.data

import com.can_apps.properties.core.PriceDomain
import com.can_apps.properties.core.PropertiesContract

internal class PropertiesRepository : PropertiesContract.Repository {

    override suspend fun getPrices(): Set<PriceDomain> {
        TODO("Not yet implemented")
    }
}