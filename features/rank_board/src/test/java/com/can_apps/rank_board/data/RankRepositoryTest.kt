package com.can_apps.rank_board.data

import com.can_apps.rank_board.core.RankProfileDomain
import com.can_apps.rank_data_source.RankDataSource
import com.can_apps.rank_data_source.RankDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class RankRepositoryTest {

    @MockK
    private lateinit var dataSource: RankDataSource

    @MockK
    private lateinit var mapper: RankDtoMapper

    @InjectMockKs
    private lateinit var repository: RankRepository

    @Before
    fun setup() = MockKAnnotations.init(this, relaxed = true)

    @Test
    fun `GIVEN dto empty, WHEN get profiles, THEN empty`() {
        // GIVEN
        val dto = RankDto(emptySet())
        val expected = emptySet<RankProfileDomain>()

        coEvery { dataSource.getAll() } returns dto
        every { mapper.toDomain(dto.profiles) } returns expected

        // WHEN
        val result = runBlocking { repository.getProfiles() }

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN dto, WHEN get profiles, THEN domain`() {
        // GIVEN
        val dto = mockk<RankDto>(relaxed = true)
        val domain = mockk<RankProfileDomain>(relaxed = true)
        val expected = setOf(domain)

        coEvery { dataSource.getAll() } returns dto
        every { mapper.toDomain(dto.profiles) } returns expected

        // WHEN
        val result = runBlocking { repository.getProfiles() }

        // THEN
        assertEquals(expected, result)
    }
}
