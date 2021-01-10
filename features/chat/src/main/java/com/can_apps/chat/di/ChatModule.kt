package com.can_apps.chat.di

import com.can_apps.chat.app.adapter.ChatAdapter
import com.can_apps.chat.bresenter.ChatModelMapper
import com.can_apps.chat.bresenter.ChatModelMapperDefault
import com.can_apps.chat.bresenter.ChatPresenter
import com.can_apps.chat.core.ChatContract
import com.can_apps.chat.core.ChatInteractor
import com.can_apps.chat.data.ChatDtoMapper
import com.can_apps.chat.data.ChatDtoMapperDefault
import com.can_apps.chat.data.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Named

@Module
@InstallIn(FragmentComponent::class)
internal class ChatModule {

    @Provides
    internal fun provideChatAdapter(): ChatAdapter = ChatAdapter()

    @Named("debouncedWait")
    @Provides
    internal fun provideDebounceWait(): Long = SECOND_IN_MILLIS

    companion object {

        private const val SECOND_IN_MILLIS =
            2000L // to test 20s tail rule need to increase this number. e.g 120000 (2min)
    }
}

@Suppress("unused")
@Module
@InstallIn(FragmentComponent::class)
internal abstract class BindingChatModule {

    @Binds
    internal abstract fun bindChatModelMapper(bound: ChatModelMapperDefault): ChatModelMapper

    @Binds
    internal abstract fun bindChatPresenter(bound: ChatPresenter): ChatContract.Presenter

    @Binds
    internal abstract fun bindChatInteractor(bound: ChatInteractor): ChatContract.Interactor

    @Binds
    internal abstract fun bindChatDtoMapper(bound: ChatDtoMapperDefault): ChatDtoMapper

    @Binds
    internal abstract fun bindChatRepository(bound: ChatRepository): ChatContract.Repository
}
