package com.can_apps.properties.bresenter

import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactory
import com.can_apps.common.coroutines.CommonCoroutineDispatcherFactoryUnconfined
import com.can_apps.properties.core.PropertiesContract
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

internal class PropertiesPresenterTest {

    @MockK
    private lateinit var dispatcher: CommonCoroutineDispatcherFactory

    @MockK
    private lateinit var interactor: PropertiesContract.Interactor

    @MockK
    private lateinit var view: PropertiesContract.View

    @InjectMockKs
    private lateinit var presenter: PropertiesPresenter

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        val unconfinedFactory = CommonCoroutineDispatcherFactoryUnconfined()
        every { dispatcher.IO } returns unconfinedFactory.IO
        every { dispatcher.UI } returns unconfinedFactory.UI

        presenter.bindView(view)
    }

    @Test
    fun `GIVEN null, WHEN view created, THEN show error`() {
        // GIVEN
        val domain = null

        coEvery { interactor.getAverage() } returns domain

        // WHEN
        presenter.onViewCreated()

        // THEN
        verify {
            view.showLoading()
            view.hideLoading()
            view.showError()
        }
        verify(exactly = 0) {
            view.updatePriceAverage(any())
        }
    }
}
