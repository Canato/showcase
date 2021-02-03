package com.can_apps.properties.core

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

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
        val domain = emptySet<PriceDomain>()
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
        val domain = setOf(PriceDomain(42F), PriceDomain(24F), PriceDomain(1F), PriceDomain(22F))
        val expected = PriceDomain(22.25F)

        coEvery { repository.getPrices() } returns domain

        // WHEN
        val result = runBlocking { interactor.getAverage() }

        // THEN
        assertEquals(expected, result)
    }
}