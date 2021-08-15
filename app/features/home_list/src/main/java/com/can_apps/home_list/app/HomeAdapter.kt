package com.can_apps.home_list.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.home_list.bresenter.HomeFeatModel
import com.can_apps.home_list.core.HomeContract
import com.can_apps.home_list.databinding.ItemHomeFeatureBinding

internal class HomeAdapter(
    private val presenter: HomeContract.Presenter
) : RecyclerView.Adapter<HomeItemViewHolder>() {

    private var items: List<HomeFeatModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder =
        HomeItemViewHolder(
            ItemHomeFeatureBinding.inflate(LayoutInflater.from(parent.context), parent, false),
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
