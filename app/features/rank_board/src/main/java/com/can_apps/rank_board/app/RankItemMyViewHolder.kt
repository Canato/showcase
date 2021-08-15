package com.can_apps.rank_board.app

import androidx.recyclerview.widget.RecyclerView
import com.can_apps.rank_board.bresenter.RankModel
import com.can_apps.rank_board.databinding.ItemMyProfileBinding

internal class RankItemMyViewHolder(
    private val binding: ItemMyProfileBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: RankModel, position: Int) {
        binding.position.text = (position + 1).toString()
        binding.username.text = model.username.value
        binding.weekXp.text = model.weeklyXP.value
    }
}
