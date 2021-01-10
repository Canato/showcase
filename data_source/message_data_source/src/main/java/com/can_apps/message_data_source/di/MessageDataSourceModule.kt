package com.can_apps.message_data_source.di

import android.content.Context
import com.can_apps.message_data_source.MessageDao
import com.can_apps.message_data_source.MessageDaoMapper
import com.can_apps.message_data_source.MessageDaoMapperDefault
import com.can_apps.message_data_source.MessageDatabaseDataSource
import com.can_apps.message_data_source.MessageDatabaseDataSourceDefault
import com.can_apps.message_data_source.MessageDatabaseProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class MessageDataSourceModule {

    @Provides
    internal fun provideMessageDao(@ApplicationContext context: Context) : MessageDao {
        return MessageDatabaseProvider.getInstance(context).getMessageDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BindingMessageDataSourceModule {

    @Binds
    internal abstract fun bindMessageDatabaseDataSource(bound: MessageDatabaseDataSourceDefault): MessageDatabaseDataSource

    @Binds
    internal abstract fun bindMessageDaoMapperDefault(bound: MessageDaoMapperDefault): MessageDaoMapper
}