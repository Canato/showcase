package com.can_apps.rank_board.di

import com.can_apps.rank_board.app.RankAdapter
import com.can_apps.rank_board.bresenter.RankModelMapper
import com.can_apps.rank_board.bresenter.RankModelMapperDefault
import com.can_apps.rank_board.bresenter.RankPresenter
import com.can_apps.rank_board.core.RankContract
import com.can_apps.rank_board.core.RankInteractor
import com.can_apps.rank_board.data.RankDtoMapper
import com.can_apps.rank_board.data.RankDtoMapperDefault
import com.can_apps.rank_board.data.RankRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
internal class RankModule {

    @Provides
    internal fun provideAdapter(): RankAdapter = RankAdapter()
}

@Suppress("unused")
@Module
@InstallIn(FragmentComponent::class)
internal abstract class BindingRankModule {
    @Binds
    internal abstract fun bindRankModelMapper(bound: RankModelMapperDefault): RankModelMapper

    @Binds
    internal abstract fun bindRankDtoMapper(bound: RankDtoMapperDefault): RankDtoMapper

    @Binds
    internal abstract fun bindRankPresenter(bound: RankPresenter): RankContract.Presenter

    @Binds
    internal abstract fun bindRankRepository(bound: RankRepository): RankContract.Repository

    @Binds
    internal abstract fun bindRankInteractor(bound: RankInteractor): RankContract.Interactor
}
