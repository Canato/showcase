package com.can_apps.rank_data_source

import com.can_apps.rank_data_source.api.RankApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class RankDataSourceDefaultTest {

    @MockK
    private lateinit var api: RankApi

    @InjectMockKs
    private lateinit var dataSource: RankDataSourceDefault

    @Before
    fun setup() = MockKAnnotations.init(this, relaxed = true)

    @Test
    fun `GIVEN dto, WHEN get all, THEN return it`() {
        // GIVEN
        val dto = mockk<RankDto>(relaxed = true)

        coEvery { api.getAll() } returns dto
        // WHEN
        val result = runBlocking { dataSource.getAll() }
        // THEN
        Assert.assertEquals(dto, result)
    }

    @Test
    fun `GIVEN empty, WHEN get all, THEN return empty`() {
        // GIVEN
        val dto = RankDto(emptySet())

        coEvery { api.getAll() } returns dto
        // WHEN
        val result = runBlocking { dataSource.getAll() }
        // THEN
        Assert.assertEquals(dto, result)
    }

    @Test
    fun `GIVEN exception, WHEN get all, THEN return empty`() {
        // GIVEN
        val dto = RankDto(emptySet())
        val exception = Exception("oppsss")

        coEvery { api.getAll() } throws exception
        // WHEN
        val result = runBlocking { dataSource.getAll() }
        // THEN
        Assert.assertEquals(dto, result)
    }
}
