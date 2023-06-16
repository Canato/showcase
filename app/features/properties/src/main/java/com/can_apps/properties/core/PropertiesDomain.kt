package com.can_apps.properties.core

import java.math.BigDecimal

internal data class PropertiesDomain(
    val id: IdDomain,
    val price: PriceDomain,
)

@JvmInline
internal value class IdDomain(val value: Int)
@JvmInline
internal value class PriceDomain(val value: BigDecimal)
