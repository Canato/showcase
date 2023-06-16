package com.can_apps.home_list.bresenter

data class HomeModel(
    val features: List<HomeFeatModel>
)

data class HomeFeatModel(
    val title: HomeTitleModel,
    val detLink: HomeDestLinkModel
)

@JvmInline
value class HomeTitleModel(val value: String)
@JvmInline
value class HomeDestLinkModel(val value: Int)
