package com.can_apps.home_list.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.can_apps.home_list.R
import com.can_apps.home_list.bresenter.HomeFeatModel
import com.can_apps.home_list.core.HomeContract
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter
    private lateinit var homeAdapter: HomeAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val serviceLocator = HomeServiceLocator(context)

        presenter = serviceLocator.getPresenter()
        homeAdapter = serviceLocator.getAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.bind(this)
        setupActionbar()
        setupRecyclerView()
        presenter.onViewCreated()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.license_dest -> {
                startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                true
            }
            // Have the NavigationUI look for an action or destination matching the menu
            // item id and navigate there if found.
            // Otherwise, bubble up to the parent.
            else -> item.onNavDestinationSelected(findNavController()) ||
                super.onOptionsItemSelected(item)
        }

    private fun setupActionbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setupRecyclerView() {
        homeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = homeAdapter
        }
    }

    override fun updateList(model: List<HomeFeatModel>) {
        homeAdapter.setupList(model)
    }

    override fun navigateToDet(model: HomeFeatModel, layout: LinearLayout) {
        val extras = FragmentNavigatorExtras(
            layout to model.title.value
        )
        val bundle = Bundle()
        bundle.putString("transitionNameArg", model.title.value)

        findNavController().navigate(model.detLink.value, bundle, null, extras)
    }

    override fun onDestroyView() {
        presenter.unbind()
        super.onDestroyView()
    }
}
