package com.can_apps.rank_board.core

import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryUnconfined
import com.can_apps.common.wrappers.CommonCalendarWrapper
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
    fun `GIVEN empty profiles, WHEN init, THEN return empty state`() {
        // GIVEN
        val weekDay = 6
        val profiles = emptySet<RankProfileDomain>()

        coEvery { repository.getProfiles() } returns profiles
        coEvery { calendarWrapper.getDayOfWeek() } returns weekDay

        val expected = RankDomain.Empty
        // WHEN
        val result = runBlocking { interactor.getInitialState() }

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN saturday and profiles, WHEN init, THEN return domain`() {
        // GIVEN
        val weekDay = 6
        val resetTime = RankResetTimeDomain(3)
        val profileA = mockk<RankProfileDomain>()
        val profileB = mockk<RankProfileDomain>()
        val profiles = setOf(profileA, profileB)

        coEvery { repository.getProfiles() } returns profiles
        coEvery { calendarWrapper.getDayOfWeek() } returns weekDay

        val expected = RankDomain.Valid(profiles, resetTime)
        // WHEN
        val result = runBlocking { interactor.getInitialState() }

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN sunday and profiles, WHEN init, THEN return domain`() {
        // GIVEN
        val weekDay = 7
        val resetTime = RankResetTimeDomain(2)
        val profileA = mockk<RankProfileDomain>()
        val profileB = mockk<RankProfileDomain>()
        val profiles = setOf(profileA, profileB)

        coEvery { repository.getProfiles() } returns profiles
        coEvery { calendarWrapper.getDayOfWeek() } returns weekDay

        val expected = RankDomain.Valid(profiles, resetTime)
        // WHEN
        val result = runBlocking { interactor.getInitialState() }

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN monday and profiles, WHEN init, THEN return domain`() {
        // GIVEN
        val weekDay = 1
        val resetTime = RankResetTimeDomain(8)
        val profileA = mockk<RankProfileDomain>()
        val profileB = mockk<RankProfileDomain>()
        val profiles = setOf(profileA, profileB)

        coEvery { repository.getProfiles() } returns profiles
        coEvery { calendarWrapper.getDayOfWeek() } returns weekDay

        val expected = RankDomain.Valid(profiles, resetTime)
        // WHEN
        val result = runBlocking { interactor.getInitialState() }

        // THEN
        assertEquals(expected, result)
    }
}
