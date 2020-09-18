package com.can_apps.home_list.app

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.can_apps.home_list.R
import com.can_apps.home_list.bresenter.HomeFeatModel
import com.can_apps.home_list.core.HomeContract
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val serviceLocator = HomeServiceLocator(context)

        presenter = serviceLocator.getPresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.bind(this)
//        homeText.setOnClickListener {
//            findNavController().navigate(R.id.action_home_dest_to_rank_dest, null)
//        }
        setupRecyclerView()
        presenter.onViewCreated()
    }

    private fun setupRecyclerView() {
        TODO("Not yet implemented")
    }

    override fun updateList(model: List<HomeFeatModel>) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        presenter.unbind()
        super.onDestroyView()
    }
}
