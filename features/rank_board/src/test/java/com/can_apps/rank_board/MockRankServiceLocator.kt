package com.can_apps.rank_board

import com.can_apps.common.CommonCoroutineDispatcherFactory
import com.can_apps.common.CommonCoroutineDispatcherFactoryUnconfined
import com.can_apps.common.CommonStringResource
import com.can_apps.rank_board.app.RankServiceLocator
import io.mockk.mockk

internal class MockRankServiceLocator (
    private val mockServerUrl: String,
    private val string: CommonStringResource
) : RankServiceLocator(mockk(relaxed = true)) {

    override fun getCoroutine(): CommonCoroutineDispatcherFactory =
        CommonCoroutineDispatcherFactoryUnconfined()

//    override fun getApi(): DepopShippingApi =
//        TestDepopShippingApi(
//            Retrofit.Builder()
//                .baseUrl(mockServerUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create()
//        )

    override fun getString(): CommonStringResource = string
}