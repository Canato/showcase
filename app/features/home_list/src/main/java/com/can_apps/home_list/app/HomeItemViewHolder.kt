package com.can_apps.home_list.app

import androidx.recyclerview.widget.RecyclerView
import com.can_apps.home_list.bresenter.HomeFeatModel
import com.can_apps.home_list.core.HomeContract
import com.can_apps.home_list.databinding.ItemHomeFeatureBinding

internal class HomeItemViewHolder(
    private val binding: ItemHomeFeatureBinding,
    private val presenter: HomeContract.Presenter
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: HomeFeatModel) {
        binding.root.apply {
            transitionName = model.title.value
        }
        binding.itemHomeText.text = model.title.value
        binding.root.setOnClickListener {
            presenter.onItemClick(model, binding.itemLayout)
        }
    }
}
