package com.can_apps.rank_board.bresenter

import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryUnconfined
import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

internal class RankPresenterTest {

    @MockK
    private lateinit var dispatcher: CommonCoroutineDispatcherFactory

    @MockK
    private lateinit var interactor: RankContract.Interactor

    @MockK
    private lateinit var modelMapper: RankModelMapper

    @MockK
    private lateinit var view: RankContract.View

    @InjectMockKs
    private lateinit var presenter: RankPresenter

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        val unconfinedFactory = CommonCoroutineDispatcherFactoryUnconfined()
        every { dispatcher.IO } returns unconfinedFactory.IO
        every { dispatcher.UI } returns unconfinedFactory.UI

        presenter.bindView(view)
    }

    @Test
    fun `GIVEN empty domain, WHEN view created, THEN show error`() {
        // GIVEN
        val domain = RankDomain.Empty

        coEvery { interactor.getInitialState() } returns domain

        // WHEN
        presenter.onViewCreated()

        // THEN
        verify {
            view.showLoading()
            view.hideLoading()
            view.showError()
            view.updateResetTime("0")
        }
    }
}
