package com.can_apps.chat.app

import android.content.Context
import android.os.Bundle
import android.view.View
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
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment(R.layout.fragment_chat), ChatContract.View {

    private lateinit var presenter: ChatContract.Presenter
    private lateinit var recyclerViewAdapter: ChatAdapter
    private val args: ChatFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val serviceLocator = ChatServiceLocator(context)
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

        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setupTextInput() {
        chatSendButton.setOnClickListener {
            val message = chatInputText.text.toString()
            chatInputText.text.clear()
            presenter.onSendMessage(ChatMessageTextModel(message))
        }
    }

    private fun setupRecyclerView() {
        chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false).apply {
                stackFromEnd = false
                reverseLayout = true
            }
            adapter = recyclerViewAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupAnimations() {
        chatLayout.apply {
            transitionName = args.transitionNameArg
        }
    }

    override fun addMessage(message: ChatMessageModel) {
        recyclerViewAdapter.addToList(message)
        chatRecyclerView.smoothScrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbind()
    }
}
