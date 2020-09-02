package com.can_apps.rank_board

import com.can_apps.common.CommonCalendarWrapper
import com.can_apps.common.CommonStringResource
import com.can_apps.rank_board.bresenter.RankModel
import com.can_apps.rank_board.bresenter.RankXpModel
import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankUsernameDomain
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class RankIntegrationTest {

    @JvmField
    @Rule
    val mockWebServerRule = MockWebServer()

    @MockK
    private lateinit var string: CommonStringResource

    @MockK
    private lateinit var calendarWrapper: CommonCalendarWrapper

    @MockK
    private lateinit var view: RankContract.View

    private lateinit var presenter: RankContract.Presenter

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        val mockServerUrl = mockWebServerRule.url("/").toString()

        val serviceLocator = MockRankServiceLocator(mockServerUrl, string, calendarWrapper)
        presenter = serviceLocator.getPresenter()

        presenter.bindView(view)
    }

    @Test
    fun `GIVEN data, WHEN init, THEN update list`() {
        // MockWebServer
        val response = MockResponse()
            .setResponseCode(200)
            .setBodyFromResource("integration/leaderboard.json")
        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse =
                when (request.path) {
                    "/api/v1/stub/leaderboard" -> response
                    else -> MockResponse().setResponseCode(404)
                }
        }
        mockWebServerRule.dispatcher = dispatcher

        // GIVEN
        val reset = "3 days"
        val username = RankUsernameDomain("Post Malone")
        val weekXp = RankXpModel("42 XP")
        val myUsername = RankUsernameDomain("Hungria")
        val myWeekXp = RankXpModel("999 XP")
        val model = listOf(
            RankModel.MyOwn(myUsername, myWeekXp),
            RankModel.Profile(username, weekXp)
        )

        every { calendarWrapper.getDayOfWeek() } returns 6
        every { string.getString(R.string.days) } returns "days"

        // WHEN
        presenter.onViewCreated()

        // THEN
        verify {
            view.showLoading()
            view.hideLoading()
            view.updateResetTime(reset)
            view.updateRankList(model)
        }
    }

    @Test
    fun `GIVEN Sunday, WHEN init, THEN reset tomorrow`() {
        // MockWebServer
        val response = MockResponse()
            .setResponseCode(200)
            .setBodyFromResource("integration/leaderboard.json")
        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse =
                when (request.path) {
                    "/api/v1/stub/leaderboard" -> response
                    else -> MockResponse().setResponseCode(404)
                }
        }
        mockWebServerRule.dispatcher = dispatcher

        // GIVEN
        val reset = "Intergalactic"
        val username = RankUsernameDomain("Post Malone")
        val weekXp = RankXpModel("42 XP")
        val myUsername = RankUsernameDomain("Hungria")
        val myWeekXp = RankXpModel("999 XP")
        val model = listOf(
            RankModel.MyOwn(myUsername, myWeekXp),
            RankModel.Profile(username, weekXp)
        )

        every { calendarWrapper.getDayOfWeek() } returns 1
        every { string.getString(R.string.tomorrow) } returns reset

        // WHEN
        presenter.onViewCreated()

        // THEN
        verify {
            view.showLoading()
            view.hideLoading()
            view.updateResetTime(reset)
            view.updateRankList(model)
        }
    }
}

internal fun MockResponse.setBodyFromResource(path: String): MockResponse {
    val fileContent = this.javaClass.classLoader?.getResource(path)?.readText()
    return setBody(fileContent ?: "")
}
