package com.can_apps.home_list.core

import com.can_apps.home_list.R

internal class HomeInteractor : HomeContract.Interactor {

    override fun fetchFeatures(): HomeDomain =
        HomeDomain(
            listOf(
                HomeFeatDomain(HomeEnumDomain.RANK, HomeDestLinkDomain(R.id.action_home_dest_to_rank_dest)),
                HomeFeatDomain(HomeEnumDomain.CHAT, HomeDestLinkDomain(R.id.action_home_dest_to_char_dest)),
                HomeFeatDomain(HomeEnumDomain.PROPERTIES, HomeDestLinkDomain(R.id.action_home_dest_to_properties_dest)),
            )
        )
}
