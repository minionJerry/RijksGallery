package com.minionjerry.android.rijksgallery.data.remote.injection

import com.minionjerry.android.rijksgallery.data.remote.source.RemoteArtObjectDataSourceImpl
import com.minionjerry.android.rijksgallery.data.repository.source.remote.RemoteArtObjectDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun bindArtObjectDataSource(remoteArtObjectDataSourceImpl: RemoteArtObjectDataSourceImpl): RemoteArtObjectDataSource
}