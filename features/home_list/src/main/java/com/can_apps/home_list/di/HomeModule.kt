package com.can_apps.home_list.di

import com.can_apps.home_list.app.HomeAdapter
import com.can_apps.home_list.bresenter.HomeModelMapper
import com.can_apps.home_list.bresenter.HomeModelMapperDefault
import com.can_apps.home_list.bresenter.HomePresenter
import com.can_apps.home_list.core.HomeContract
import com.can_apps.home_list.core.HomeInteractor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Suppress("unused")
@Module
@InstallIn(FragmentComponent::class)
internal abstract class BindingHomeModule {

    // FragmentScoped needed here because presenter is needed both in Fragment and in Adapter so we
    // want the same instance (in a fragment lifecycle)
    @Binds
    @FragmentScoped
    internal abstract fun bindPresenter(bound: HomePresenter) : HomeContract.Presenter

    @Binds
    internal abstract fun bindInteractor(bound: HomeInteractor) : HomeContract.Interactor

    @Binds
    internal abstract fun bindHomeModelMapper(bound: HomeModelMapperDefault) : HomeModelMapper
}

@Module
@InstallIn(FragmentComponent::class)
internal class HomeModule {

    @Provides
    internal fun provideHomeAdapter(presenter: HomeContract.Presenter): HomeAdapter {
        return HomeAdapter(presenter)
    }
}

