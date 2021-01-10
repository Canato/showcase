package com.can_apps.rank_data_source.di

import com.can_apps.rank_data_source.RankDataSource
import com.can_apps.rank_data_source.RankDataSourceDefault
import com.can_apps.rank_data_source.api.Api
import com.can_apps.rank_data_source.api.RankApi
import com.can_apps.rank_data_source.api.RankApiDefault
import com.can_apps.rank_data_source.getRankDataSourceProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RankDataSourceModule {

    @Singleton
    @Provides
    internal fun provideRankDataSourceDefault(@Named("rank") retrofit: Retrofit): RankDataSource {
        return getRankDataSourceProvider(retrofit.create(Api::class.java))
    }
}