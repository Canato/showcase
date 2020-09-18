package com.can_apps.home_list.bresenter

data class HomeModel(
    val features: List<HomeFeatModel>
)

data class HomeFeatModel(
    val title: HomeTitleModel,
    val detLink: HomeDestLinkModel
)

inline class HomeTitleModel(val value: String)
inline class HomeDestLinkModel(val value: Int)