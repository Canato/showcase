package com.can_apps.chat.app

import com.can_apps.chat.app.adapter.ChatAdapter
import com.can_apps.chat.bresenter.ChatPresenter
import com.can_apps.chat.core.ChatContract

internal class ChatServiceLocator {

    fun getPresenter(): ChatContract.Presenter =
        ChatPresenter()

    fun getAdapter(): ChatAdapter =
        ChatAdapter()
}