package com.can_apps.rank_board.app

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.can_apps.common.extensions.showDefaultError
import com.can_apps.rank_board.R
import com.can_apps.rank_board.bresenter.RankModel
import com.can_apps.rank_board.core.RankContract
import kotlinx.android.synthetic.main.fragment_rank.*

class RankFragment :
    Fragment(R.layout.fragment_rank),
    RankContract.View {

    private lateinit var presenter: RankContract.Presenter
    private lateinit var recyclerViewAdapter: RankAdapter
    private val args: RankFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val serviceLocator = RankServiceLocator(context)
        presenter = serviceLocator.getPresenter()
        recyclerViewAdapter = serviceLocator.getAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.bindView(this)

        setupRecyclerView()
        setupAnimations()

        presenter.onViewCreated()
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = recyclerViewAdapter
        }
    }

    private fun setupAnimations() {
        rankLayout.apply {
            transitionName = args.transitionNameArg
        }
    }

    override fun updateResetTime(resetTime: String) {
        resetTimeText.text = resetTime
    }

    override fun updateRankList(model: List<RankModel>) {
        recyclerViewAdapter.updateList(model)
    }

    override fun showLoading() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressView.visibility = View.GONE
    }

    override fun showError() {
        showDefaultError()
    }

    override fun onDestroyView() {
        presenter.unbindView()
        super.onDestroyView()
    }
}
