package com.can_apps.rank_board.core

import com.can_apps.common.CommonCalendarWrapper
import com.can_apps.common.CommonCoroutineDispatcherFactory
import com.can_apps.common.CommonCoroutineDispatcherFactoryUnconfined
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

internal class RankInteractorTest {

    @MockK
    private lateinit var repository: RankContract.Repository

    @MockK
    private lateinit var calendarWrapper: CommonCalendarWrapper

    @MockK
    private lateinit var dispatcher: CommonCoroutineDispatcherFactory

    @InjectMockKs
    private lateinit var interactor: RankInteractor

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        val unconfinedFactory = CommonCoroutineDispatcherFactoryUnconfined()
        every { dispatcher.IO } returns unconfinedFactory.IO
        every { dispatcher.UI } returns unconfinedFactory.UI
    }

    @Test
    fun `GIVEN saturday and profiles, WHEN init, THEN return domain`() {
        // GIVEN
        val weekDay = 6
        val resetTime = RankResetTimeDomain(2)
        val profileA = mockk<RankProfileDomain>()
        val profileB = mockk<RankProfileDomain>()
        val profiles = setOf(profileA, profileB)

        coEvery { repository.getProfiles() } returns profiles
        coEvery { calendarWrapper.getDayOfWeek() } returns weekDay

        val expected = RankDomain(profiles, resetTime)
        // WHEN
        val result = runBlocking { interactor.getInitialState() }

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN sunday and profiles, WHEN init, THEN return domain`() {
        // GIVEN
        val weekDay = 7
        val resetTime = RankResetTimeDomain(1)
        val profileA = mockk<RankProfileDomain>()
        val profileB = mockk<RankProfileDomain>()
        val profiles = setOf(profileA, profileB)

        coEvery { repository.getProfiles() } returns profiles
        coEvery { calendarWrapper.getDayOfWeek() } returns weekDay

        val expected = RankDomain(profiles, resetTime)
        // WHEN
        val result = runBlocking { interactor.getInitialState() }

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN monday and profiles, WHEN init, THEN return domain`() {
        // GIVEN
        val weekDay = 1
        val resetTime = RankResetTimeDomain(7)
        val profileA = mockk<RankProfileDomain>()
        val profileB = mockk<RankProfileDomain>()
        val profiles = setOf(profileA, profileB)

        coEvery { repository.getProfiles() } returns profiles
        coEvery { calendarWrapper.getDayOfWeek() } returns weekDay

        val expected = RankDomain(profiles, resetTime)
        // WHEN
        val result = runBlocking { interactor.getInitialState() }

        // THEN
        assertEquals(expected, result)
    }

}