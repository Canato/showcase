package com.can_apps.average_data_source

import com.can_apps.average_data_source.api.PropertiesApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class PropertiesDataSourceDefaultTest {

    @MockK
    private lateinit var api: PropertiesApi

    @InjectMockKs
    private lateinit var dataSource: PropertiesDataSourceDefault

    @Before
    fun setup() = MockKAnnotations.init(this, relaxed = true)

    @Test
    fun `GIVEN valid prices, WHEN getPrices, THEN return set of it`() {
        // GIVEN
        val price1 = PriceDto(42F)
        val price2 = PriceDto(24F)
        val price3 = PriceDto(99F)
        val expected = setOf(price1, price2, price3)
        val dto = PropertiesDto(
            setOf(
                PropertyInfoDto(price1),
                PropertyInfoDto(price2),
                PropertyInfoDto(price3)
            )
        )

        coEvery { api.getProperties() } returns dto

        // WHEN
        val result = runBlocking { dataSource.getPrices() }

        // THEN
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `GIVEN empty, WHEN getPrices, THEN return empty`() {
        // GIVEN
        val expected = emptySet<PriceDto>()
        val dto = PropertiesDto(emptySet())

        coEvery { api.getProperties() } returns dto

        // WHEN
        val result = runBlocking { dataSource.getPrices() }

        // THEN
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `GIVEN exception, WHEN get all, THEN return empty`() {
        // GIVEN
        val expected = emptySet<PriceDto>()
        val exception = Exception("oppsss")

        coEvery { api.getProperties() } throws exception

        // WHEN
        val result = runBlocking { dataSource.getPrices() }

        // THEN
        Assert.assertEquals(expected, result)
    }
}