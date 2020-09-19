package com.can_apps.home_list.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.home_list.R
import com.can_apps.home_list.bresenter.HomeDestLinkModel
import com.can_apps.home_list.bresenter.HomeFeatModel
import com.can_apps.home_list.core.HomeContract

internal class HomeAdapter(
    private val presenter: HomeContract.Presenter
) : RecyclerView.Adapter<HomeItemViewHolder>() {

    private var items: List<HomeFeatModel> = emptyList()

    override fun getItemViewType(position: Int): Int = R.layout.item_home_feature

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder =
        HomeItemViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false),
            presenter
        )

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setupList(model: List<HomeFeatModel>) {
        items = model
        notifyDataSetChanged()
    }
}