package com.can_apps.properties.core

import java.math.BigDecimal

internal class PropertiesInteractor(
    private val repository: PropertiesContract.Repository
) : PropertiesContract.Interactor {

    override suspend fun getAverage(): PriceDomain? =
        repository.getPrices().run {
            if (this.isEmpty()) null
            else PriceDomain(this.map { it.value }.averageBigDecimal() )
        }

//    {
//        val domain = repository.getPrices()
//
//        return if (domain.isEmpty()) null
//        else PriceDomain(domain.map { it.value }.average().toFloat() )
//    }
}

private fun List<BigDecimal>.averageBigDecimal(): BigDecimal {
    val averageDouble = this.map { it.toDouble() }.average()

    return averageDouble.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP)
}
