package com.can_apps.chat.app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.can_apps.chat.R
import com.can_apps.chat.app.adapter.ChatAdapter
import com.can_apps.chat.bresenter.ChatMessageModel
import com.can_apps.chat.bresenter.ChatMessageTextModel
import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.databinding.FragmentChatBinding

class ChatFragment : Fragment(), ChatContract.View {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ChatContract.Presenter
    private lateinit var recyclerViewAdapter: ChatAdapter
    private val args: ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val serviceLocator = ChatServiceLocator(context)
        presenter = serviceLocator.getPresenter()
        recyclerViewAdapter = serviceLocator.getAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = context?.let { TransitionInflater.from(it).inflateTransition(android.R.transition.move) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.bind(this)

        setupActionbar()
        setupRecyclerView()
        setupTextInput()
        setupAnimations()

        presenter.onViewCreated()
    }

    private fun setupActionbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setupTextInput() {
        binding.chatSendButton.setOnClickListener {
            val message = binding.chatInputText.text.toString()
            presenter.onSendMessage(ChatMessageTextModel(message))
        }
    }

    override fun showTextAnimation(message: ChatMessageTextModel) {
        binding.chatInputTextAnimation.text = message.value
        binding.chatInputTextAnimation
            .animate()
            .translationY(-130F)
            .translationX(175F)
            .alpha(0.0f)
            .setDuration(150)
            .setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        binding.chatInputTextAnimation.text = ""
                        binding.chatInputTextAnimation
                            .animate()
                            .translationY(0F)
                            .translationX(0F)
                            .alpha(1f)
                            .setDuration(1)
                            .setListener(null)
                            .start()
                    }
                }
            )
            .start()
        binding.chatInputText.text.clear()
    }

    private fun setupRecyclerView() {
        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false).apply {
                stackFromEnd = false
                reverseLayout = true
            }
            adapter = recyclerViewAdapter
        }
    }

    private fun setupAnimations() {
        binding.chatLayout.apply {
            transitionName = args.transitionNameArg
        }
    }

    override fun addMessage(message: ChatMessageModel) {
        recyclerViewAdapter.addToList(message)
        binding.chatRecyclerView.smoothScrollToPosition(0)
    }

    override fun onDestroyView() {
        presenter.unbind()
        _binding = null
        super.onDestroyView()
    }
}
