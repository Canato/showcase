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

    interface Interactor { // todo canato

        fun getSystemAnswer(message: ChatMessageTextDomain): ChatMessageTextDomain
    }

    interface Interactor {

        fun getSystemAnswer(domain: ChatDomain): ChatDomain
    }

    interface Repository {

        suspend fun addMessage(domain: ChatDomain)

        suspend fun getMessages(): List<ChatDomain>

        fun getLatest(): Flow<ChatDomain>
    }
}
