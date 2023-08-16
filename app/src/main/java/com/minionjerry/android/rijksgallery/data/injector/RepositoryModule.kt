package com.minionjerry.android.rijksgallery.data.injector

import com.minionjerry.android.rijksgallery.data.repository.ArtObjectRepositoryImpl
import com.minionjerry.android.rijksgallery.domain.repository.ArtObjectRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindArtObjectRepository(artObjectRepositoryImpl: ArtObjectRepositoryImpl): ArtObjectRepository
}