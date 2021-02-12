package com.can_apps.properties.core

import java.math.BigDecimal

internal data class PropertiesDomain(
    val id: IdDomain,
    val price: PriceDomain,
)

internal inline class IdDomain(val value: Int)
internal inline class PriceDomain(val value: BigDecimal)
