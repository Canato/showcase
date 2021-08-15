package com.can_apps.common.extensions

import java.math.BigDecimal

fun List<BigDecimal>.averageBigDecimal(): BigDecimal =
    this
        .map { it.toDouble() }
        .average()
        .toBigDecimal()
        .setScale(2, BigDecimal.ROUND_HALF_UP)
