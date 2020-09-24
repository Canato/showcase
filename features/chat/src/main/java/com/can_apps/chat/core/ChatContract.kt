package com.can_apps.chat.core

import com.can_apps.chat.bresenter.ChatMessageModel
import com.can_apps.chat.bresenter.ChatMessageTextModel
import kotlinx.coroutines.flow.Flow

internal interface ChatContract {

    interface View {

        fun setupMessages(messages: List<ChatMessageModel>)

        fun addMessage(message: ChatMessageModel)
    }

    interface Presenter {

        fun bind(view: View)

        fun unbind()

        fun onViewCreated()

        fun onSendMessage(message: ChatMessageTextModel)
    }

    interface Interactor {

        fun getSystemAnswer(domain: ChatNewDomain): ChatNewDomain

        suspend fun addMessage(domain: ChatNewDomain)

        suspend fun getMessages(): Flow<ChatDomain>

        fun getLatest(): Flow<ChatDomain>
    }

    interface Repository {

        suspend fun addMessage(domain: ChatNewDomain)

        suspend fun getMessages(): List<ChatDomain>

        fun getLatest(): Flow<ChatDomain>
    }
}
