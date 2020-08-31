package com.can_apps.bad_char_data_source

import com.can_apps.bad_char_data_source.api.BadCharApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

internal class BadCharDataSourceDefaultTest {

    @MockK
    private lateinit var api: BadCharApi

    @InjectMockKs
    private lateinit var dataSource: BadCharDataSourceDefault

    @Before
    fun setup() = MockKAnnotations.init(this, relaxed = true)

    @Test
    fun `GIVEN no cache, WHEN get all, THEN api`() {
        // GIVEN
        val dto = mockk<BadCharDto>(relaxed = true)
        val set = setOf(dto)

        coEvery { api.getAll() } returns set

        // WHEN
        val result = runBlocking { dataSource.getAll() }

        // THEN
        assertEquals(set, result)
    }

    @Test
    fun `GIVEN cache, WHEN get all, THEN cache`() {
        // GIVEN
        val dto = mockk<BadCharDto>(relaxed = true)
        val setCache = setOf(dto)

        coEvery { api.getAll() } returns setCache
        runBlocking { dataSource.getAll() }

        val setApi = setOf(dto, dto, dto, dto)
        coEvery { api.getAll() } returns setApi

        // WHEN
        val result = runBlocking { dataSource.getAll() }

        // THEN
        assertEquals(setCache, result)
    }

    @Test
    fun `GIVEN no cache, WHEN get search name, THEN api`() {
        // GIVEN
        val param = BadCharNameDto("All Star")
        val dto = mockk<BadCharDto>(relaxed = true)
        val set = setOf(dto)

        coEvery { api.getByName(param.value) } returns set

        // WHEN
        val result = runBlocking { dataSource.getSearchName(param) }

        // THEN
        assertEquals(set, result)
    }

    @Test
    fun `GIVEN cache, WHEN get search name, THEN cache`() {
        // GIVEN
        val param = BadCharNameDto("All Star")
        val dto = mockk<BadCharDto>(relaxed = true)
        val setCache = setOf(dto)

        coEvery { api.getByName(param.value) } returns setCache
        runBlocking { dataSource.getSearchName(param) }

        val setApi = setOf(dto, dto, dto, dto)
        coEvery { api.getByName(param.value) } returns setApi

        // WHEN
        val result = runBlocking { dataSource.getSearchName(param) }

        // THEN
        assertEquals(setCache, result)
    }

    @Test
    fun `GIVEN no cache, WHEN get season filter, THEN api`() {
        // GIVEN
        val param = BadCharSeasonDto(42)
        val dto = mockk<BadCharDto>(relaxed = true)
        val set = setOf(dto)

        coEvery { api.getBySeason(param.value) } returns set

        // WHEN
        val result = runBlocking { dataSource.getSeasonFilter(param) }

        // THEN
        assertEquals(set, result)
    }

    @Test
    fun `GIVEN cache, WHEN get season filter, THEN cache`() {
        // GIVEN
        val param = BadCharSeasonDto(42)
        val dto = mockk<BadCharDto>(relaxed = true)
        val setCache = setOf(dto)

        coEvery { api.getBySeason(param.value) } returns setCache
        runBlocking { dataSource.getSeasonFilter(param) }

        val setApi = setOf(dto, dto, dto, dto)
        coEvery { api.getBySeason(param.value) } returns setApi

        // WHEN
        val result = runBlocking { dataSource.getSeasonFilter(param) }

        // THEN
        assertEquals(setCache, result)
    }

    @Test
    fun `GIVEN no cache, WHEN get by id, THEN api`() {
        // GIVEN
        val param = BadCharIdDto(42L)
        val dto = mockk<BadCharDto>(relaxed = true)

        coEvery { api.getById(param.value) } returns dto

        // WHEN
        val result = runBlocking { dataSource.getById(param) }

        // THEN
        assertEquals(dto, result)
    }
// todo canato fix
    @Test
    fun `GIVEN cache, WHEN get by id, THEN cache`() {
        // GIVEN
        val param = BadCharIdDto(42L)
        val dtoCache = mockk<BadCharDto>(relaxed = true).copy(
            id = BadCharIdDto(42L),
            name = BadCharNameDto("Lisa")
        )

        coEvery { api.getById(param.value) } returns dtoCache
        runBlocking { dataSource.getById(param) }

        val dtoApi = mockk<BadCharDto>(relaxed = true).copy(
            id = BadCharIdDto(24L),
            name = BadCharNameDto("Godofredo")
        )
        coEvery { api.getById(param.value) } returns dtoApi

        // WHEN
        val result = runBlocking { dataSource.getById(param) }

        // THEN
         assertEquals(dtoCache, result)
    }

}