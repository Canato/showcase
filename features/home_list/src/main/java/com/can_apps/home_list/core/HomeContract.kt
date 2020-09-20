package com.can_apps.home_list.core

import android.widget.LinearLayout
import com.can_apps.home_list.bresenter.HomeFeatModel

internal interface HomeContract {

    interface View {

        fun updateList(model: List<HomeFeatModel>)

        fun navigateToDet(model: HomeFeatModel, layout: LinearLayout)
    }

    interface Presenter {

        fun bind(view: View)

        fun unbind()

        fun onViewCreated()

        fun onItemClick(model: HomeFeatModel, layout: LinearLayout)
    }

    interface Interactor {

        fun fetchFeatures(): HomeDomain
    }
}
