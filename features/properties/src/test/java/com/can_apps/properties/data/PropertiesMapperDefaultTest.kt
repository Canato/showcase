package com.can_apps.properties.data

import com.can_apps.average_data_source.PriceDto
import com.can_apps.properties.core.PriceDomain
import org.junit.Assert.*
import org.junit.Test

internal class PropertiesMapperDefaultTest {

    private val mapper = PropertiesMapperDefault()

    @Test
    fun `GIVEN dto prices, WHEN map, return domain`() {
        // GIVEN
        val price1 = 42F
        val price2 = 24F
        val price3 = 69F
        val dto = setOf(PriceDto(price1), PriceDto(price2), PriceDto(price3))
        val expect = setOf(PriceDomain(price1), PriceDomain(price2), PriceDomain(price3))

        // WHEN
        val result = mapper.toDomain(dto)

        // THEN
        assertEquals(expect, result)
    }

    @Test
    fun `GIVEN dto empty, WHEN map, return empty`() {
        // GIVEN
        val dto = emptySet<PriceDto>()
        val expect = emptySet<PriceDomain>()

        // WHEN
        val result = mapper.toDomain(dto)

        // THEN
        assertEquals(expect, result)
    }
}