package com.can_apps.properties.data

import com.can_apps.average_data_source.PriceDto
import com.can_apps.average_data_source.PropertiesDataSource
import com.can_apps.properties.core.PriceDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class PropertiesRepositoryTest {

    @MockK
    private lateinit var dataSource: PropertiesDataSource

    @MockK
    private lateinit var mapper: PropertiesDtoMapper

    @InjectMockKs
    private lateinit var repository: PropertiesRepository

    @Before
    fun setup() = MockKAnnotations.init(this, relaxed = true)

    @Test
    fun `GIVEN dto empty, WHEN get prices, THEN null`() {
        // GIVEN
        val dto = emptySet<PriceDto>()
        val domain = emptySet<PriceDomain>()

        coEvery { dataSource.getPrices() } returns dto
        every { mapper.toDomain(dto) } returns domain

        // WHEN
        val result = runBlocking { repository.getPrices() }

        // THEN
        Assert.assertEquals(domain, result)
    }

    @Test
    fun `GIVEN dto, WHEN get profiles, THEN domain`() {
        // GIVEN
        val dto = setOf<PriceDto>(mockk(), mockk(), mockk())
        val domain = setOf<PriceDomain>(mockk(), mockk(), mockk())

        coEvery { dataSource.getPrices() } returns dto
        every { mapper.toDomain(dto) } returns domain

        // WHEN
        val result = runBlocking { repository.getPrices() }

        // THEN
        Assert.assertEquals(domain, result)
    }
}
