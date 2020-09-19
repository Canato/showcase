package com.can_apps.home_list.core

import com.can_apps.home_list.bresenter.HomeDestLinkModel
import com.can_apps.home_list.bresenter.HomeFeatModel

internal interface HomeContract {

    interface View {

        fun updateList(model: List<HomeFeatModel>)

        fun navigateToDet(detLink: HomeDestLinkModel)
    }

    interface Presenter {

        fun bind(view: View)

        fun unbind()

        fun onViewCreated()

        fun onItemClick(detLink: HomeDestLinkModel)
    }

    interface Interactor {

        fun fetchFeatures(): HomeDomain
    }

}