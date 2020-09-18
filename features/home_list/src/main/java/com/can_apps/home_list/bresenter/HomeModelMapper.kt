package com.can_apps.home_list.bresenter

import com.can_apps.common.wrappers.CommonStringResource
import com.can_apps.home_list.R
import com.can_apps.home_list.core.HomeDomain
import com.can_apps.home_list.core.HomeEnumDomain

internal interface HomeModelMapper {

    fun toModel(domain: HomeDomain): HomeModel
}

internal class HomeModelMapperDefault(
    private val string: CommonStringResource
) : HomeModelMapper {

    override fun toModel(domain: HomeDomain): HomeModel =
        HomeModel(domain.features.map {
            HomeFeatModel(getTitle(it.title), HomeDestLinkModel(it.destLink.value))
        })

    private fun getTitle(enum: HomeEnumDomain): HomeTitleModel =
        HomeTitleModel(
            when (enum) {
                HomeEnumDomain.RANK -> string.getString(R.string.rank_title)
                HomeEnumDomain.CHAT -> string.getString(R.string.chat_title)
            }
        )
}