package com.can_apps.rank_board.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.rank_board.R
import com.can_apps.rank_board.bresenter.RankModel

internal class RankAdapter : RecyclerView.Adapter<RankItemViewHolder>() {

    companion object {
        val profile = R.layout.item_profile
        val myOwn = R.layout.item_my_profile
    }

    private var items: List<RankModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankItemViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return RankItemViewHolder(root)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RankItemViewHolder, position: Int) {
        val item = items[position]
        holder.position.text = (position + 1).toString()
        holder.username.text = item.username.value
        holder.xp.text = item.weeklyXP.value
    }

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is RankModel.Profile -> profile
            is RankModel.MyOwn -> myOwn
        }

    fun updateList(model: List<RankModel>) {
        items = model
        notifyDataSetChanged()
    }
}
