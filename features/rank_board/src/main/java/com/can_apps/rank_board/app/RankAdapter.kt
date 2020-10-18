package com.can_apps.rank_board.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.rank_board.bresenter.RankModel
import com.can_apps.rank_board.databinding.ItemMyProfileBinding
import com.can_apps.rank_board.databinding.ItemProfileBinding

internal class RankAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<RankModel> = emptyList()

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val root = LayoutInflater.from(parent.context)
        return when (items[viewType]) {
            is RankModel.Profile -> RankItemViewHolder(ItemProfileBinding.inflate(root))
            is RankModel.MyOwn -> RankItemMyViewHolder(ItemMyProfileBinding.inflate(root)) //inflate(root, parent, false))
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (items[position]) {
            is RankModel.Profile -> (holder as RankItemViewHolder).bind(items[position], position)
            is RankModel.MyOwn ->(holder as RankItemMyViewHolder).bind(items[position], position)
        }
    }



    fun updateList(model: List<RankModel>) {
        items = model
        notifyDataSetChanged()
    }
}
