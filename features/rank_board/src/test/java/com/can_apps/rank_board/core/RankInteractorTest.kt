package com.can_apps.rank_board.core

import com.can_apps.common.CommonCalendar
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class RankInteractorTest {

    @MockK
    private lateinit var repository: RankContract.Repository

    @MockK
    private lateinit var calendar: CommonCalendar

    @InjectMockKs
    private lateinit var interactor: RankInteractor

    @Before
    fun setup() = MockKAnnotations.init(this, relaxed = true)

    @Test
    fun `GIVEN saturday and profiles, WHEN init, THEN return domain`() {
        // GIVEN
        val weekDay = 7
        val resetTime = RankResetTimeDomain(2)
        val profileA = mockk<RankProfileDomain>()
        val profileB = mockk<RankProfileDomain>()
        val profiles = setOf(profileA, profileB)

        coEvery { repository.getProfiles() } returns profiles
        coEvery { calendar.getDayOfWeek() } returns weekDay

        val expected = RankDomain(profiles, resetTime)
        // WHEN
        val result = runBlocking { interactor.getInitialState() }

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN sunday and profiles, WHEN init, THEN return domain`() {
        // GIVEN
        val weekDay = 1
        val resetTime = RankResetTimeDomain(0)
        val profileA = mockk<RankProfileDomain>()
        val profileB = mockk<RankProfileDomain>()
        val profiles = setOf(profileA, profileB)

        coEvery { repository.getProfiles() } returns profiles
        coEvery { calendar.getDayOfWeek() } returns weekDay

        val expected = RankDomain(profiles, resetTime)
        // WHEN
        val result = runBlocking { interactor.getInitialState() }

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN monday and profiles, WHEN init, THEN return domain`() {
        // GIVEN
        val weekDay = 2
        val resetTime = RankResetTimeDomain(7)
        val profileA = mockk<RankProfileDomain>()
        val profileB = mockk<RankProfileDomain>()
        val profiles = setOf(profileA, profileB)

        coEvery { repository.getProfiles() } returns profiles
        coEvery { calendar.getDayOfWeek() } returns weekDay

        val expected = RankDomain(profiles, resetTime)
        // WHEN
        val result = runBlocking { interactor.getInitialState() }

        // THEN
        assertEquals(expected, result)
    }

}