package com.can_apps.rank_board.app

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_profile.view.position
import kotlinx.android.synthetic.main.item_profile.view.username
import kotlinx.android.synthetic.main.item_profile.view.weekXp

internal class RankItemViewHolder(root: View) : RecyclerView.ViewHolder(root) {

    val position: TextView = root.position
    val username: TextView = root.username
    val xp: TextView = root.weekXp
}
