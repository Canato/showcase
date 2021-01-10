

package com.can_apps.common.di

import android.content.Context
import com.can_apps.common.network.CommonHttpClientProvider
import com.can_apps.common.wrappers.CommonCalendarWrapper
import com.can_apps.common.wrappers.CommonCalendarWrapperDefault
import com.can_apps.common.wrappers.CommonTimestampWrapper
import com.can_apps.common.wrappers.CommonTimestampWrapperDefault
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal abstract class BindingCommonModule {

    @Binds
    internal abstract fun bindCalendarWrapper(bound: CommonCalendarWrapperDefault): CommonCalendarWrapper

    @Binds
    internal abstract fun bindCommonTimestampWrapper(bound: CommonTimestampWrapperDefault): CommonTimestampWrapper
}

@Module
@InstallIn(SingletonComponent::class)
internal class CommonModule {

    @Singleton
    @Provides
    @Named("rank")
    internal fun provideRankRetrofit(@ApplicationContext context: Context): Retrofit {
        return CommonHttpClientProvider(context).buildRank()
    }

    @Provides
    @Named("ui")
    internal fun provideUiDispatcher(): CoroutineContext = Dispatchers.Main

    @Provides
    @Named("io")
    internal fun bindIoDispatcher(): CoroutineContext = Dispatchers.IO

}
