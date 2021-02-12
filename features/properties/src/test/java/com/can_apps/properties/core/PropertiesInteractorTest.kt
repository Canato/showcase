package com.can_apps.properties.core

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

internal class PropertiesInteractorTest {

    @MockK
    private lateinit var repository: PropertiesContract.Repository

    @InjectMockKs
    private lateinit var interactor: PropertiesInteractor

    @Before
    fun setup() = MockKAnnotations.init(this, relaxed = true)

    @Test
    fun `GIVEN empty, WHEN getAverage, THEN null`() {
        // GIVEN
        val domain = emptySet<PropertiesDomain>()
        val expected = null

        coEvery { repository.getPrices() } returns domain

        // WHEN
        val result = runBlocking { interactor.getAverage() }

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN values, WHEN getAverage, THEN average`() {
        // GIVEN
        val domain = setOf(
            PropertiesDomain(IdDomain(1), PriceDomain(BigDecimal(42))),
            PropertiesDomain(IdDomain(2), PriceDomain(BigDecimal(24))),
            PropertiesDomain(IdDomain(3), PriceDomain(BigDecimal(1))),
            PropertiesDomain(IdDomain(4), PriceDomain(BigDecimal(22))),
        )
        val expected = PriceDomain(BigDecimal(22.25))

        coEvery { repository.getPrices() } returns domain

        // WHEN
        val result = runBlocking { interactor.getAverage() }

        // THEN
        assertEquals(expected, result)
    }
}
