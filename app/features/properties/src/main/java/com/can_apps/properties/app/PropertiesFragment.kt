package com.can_apps.properties.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.can_apps.common.extensions.hideFadeOut
import com.can_apps.common.extensions.showDefaultError
import com.can_apps.common.extensions.showFadeIn
import com.can_apps.properties.core.PropertiesContract
import com.can_apps.properties.databinding.FragmentPropertiesBinding

class PropertiesFragment : Fragment(), PropertiesContract.View {

    private var binding: FragmentPropertiesBinding? = null

    private var presenter: PropertiesContract.Presenter? = null
    private val args: PropertiesFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val serviceLocator = PropertiesServiceLocator(context)
        presenter = serviceLocator.getPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPropertiesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            context?.let { TransitionInflater.from(it).inflateTransition(android.R.transition.move) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter?.bindView(this)

        setupAnimations()

        presenter?.onViewCreated()
    }

    private fun setupAnimations() {
        binding?.propertiesLayout?.apply {
            transitionName = args.transitionNameArg
        }
    }

    override fun updatePriceAverage(priceAverage: String) {
        binding?.response?.apply {
            showFadeIn()
            text = priceAverage
        }
    }

    override fun showError() {
        showDefaultError()
    }

    override fun showLoading() {
        binding?.progress?.showFadeIn()
    }

    override fun hideLoading() {
        binding?.progress?.hideFadeOut()
    }
}
