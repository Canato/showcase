package com.can_apps.rank_board.app

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.can_apps.common.extensions.showDefaultError
import com.can_apps.rank_board.bresenter.RankModel
import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.databinding.FragmentRankBinding

class RankFragment : Fragment(), RankContract.View {

    private var _binding: FragmentRankBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: RankContract.Presenter
    private lateinit var recyclerViewAdapter: RankAdapter
    private val args: RankFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val serviceLocator = RankServiceLocator(context)
        presenter = serviceLocator.getPresenter()
        recyclerViewAdapter = serviceLocator.getAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            context?.let { TransitionInflater.from(it).inflateTransition(android.R.transition.move) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.bindView(this)

        setupRecyclerView()
        setupAnimations()

        presenter.onViewCreated()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = recyclerViewAdapter
        }
    }

    private fun setupAnimations() {
        binding.rankLayout.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                transitionName = args.transitionNameArg
            }
        }
    }

    override fun updateResetTime(resetTime: String) {
        binding.resetTimeText.text = resetTime
    }

    override fun updateRankList(model: List<RankModel>) {
        recyclerViewAdapter.updateList(model)
    }

    override fun showLoading() {
        binding.progressView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressView.visibility = View.GONE
    }

    override fun showError() {
        showDefaultError()
    }

    override fun onDestroyView() {
        presenter.unbindView()
        _binding = null
        super.onDestroyView()
    }
}
