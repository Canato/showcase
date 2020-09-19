package com.can_apps.home_list.app

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.home_list.bresenter.HomeFeatModel
import com.can_apps.home_list.core.HomeContract
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_home_feature.view.*

internal class HomeItemViewHolder(
    private val root: View,
    private val presenter: HomeContract.Presenter
) : RecyclerView.ViewHolder(root) {

    fun bind(model: HomeFeatModel) {
        root.apply {
            transitionName = model.title.value
        }
        root.itemHomeText.text = model.title.value
        root.setOnClickListener {
            presenter.onItemClick(model, it.itemLayout)
        }
    }
}
