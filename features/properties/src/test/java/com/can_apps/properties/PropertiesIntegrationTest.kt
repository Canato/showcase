package com.can_apps.properties

import com.can_apps.properties.core.PropertiesContract
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@Ignore
internal class PropertiesIntegrationTest {

    @JvmField
    @Rule
    val mockWebServerRule = MockWebServer()

    @MockK
    private lateinit var view: PropertiesContract.View

    private lateinit var presenter: PropertiesContract.Presenter

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        val mockServerUrl = mockWebServerRule.url("/").toString()

        val serviceLocator = MockPropertiesServiceLocator(mockServerUrl)
        presenter = serviceLocator.getPresenter()

        presenter.bindView(view)
    }

    @Test
    fun `GIVEN values, WHEN init, THEN show average`() {
        // MockWebServer
        val response = MockResponse()
            .setResponseCode(200)
            .setBodyFromResource("integration/get_properties_success.json")
        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse =
                when (request.path) {
                    "/rightmove/Code-Challenge-Android/master/properties.json" -> response
                    else -> MockResponse().setResponseCode(404)
                }
        }
        mockWebServerRule.dispatcher = dispatcher

        // GIVEN
        val expected = "410,280.78"

        // WHEN
        presenter.onViewCreated()

        // THEN
        verify {
            view.showLoading()
            view.hideLoading()
            view.updatePriceAverage(expected)
        }
    }
}

internal fun MockResponse.setBodyFromResource(path: String): MockResponse {
    val fileContent = this.javaClass.classLoader?.getResource(path)?.readText()
    return setBody(fileContent ?: "")
}
