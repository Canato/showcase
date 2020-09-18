package com.can_apps.home_list.core

import com.can_apps.home_list.bresenter.HomeFeatModel

internal interface HomeContract {

    interface View {

        fun updateList(model: List<HomeFeatModel>)
    }

    interface Presenter {

        fun bind(view: View)

        fun unbind()

        fun onViewCreated()
    }

    interface Interactor {

        fun fetchFeatures(): HomeDomain
    }

}