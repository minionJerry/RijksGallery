package com.minionjerry.android.rijksgallery.data.repository

import com.minionjerry.android.rijksgallery.data.repository.source.local.LocalArtObjectDataSource
import com.minionjerry.android.rijksgallery.data.repository.source.remote.RemoteArtObjectDataSource
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.repository.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ArtObjectRepositoryImpl @Inject constructor(
    private val remoteArtObjectDataSource: RemoteArtObjectDataSource,
    private val localArtObjectDataSource: LocalArtObjectDataSource
    ): ArtObjectRepository {
    override fun getArtObjects(): Flow<List<ArtObject>> {
        return remoteArtObjectDataSource.getArtObjects()
            .onEach {
                localArtObjectDataSource.addArtObjects(it)
            }
    }

    override fun getArtObject(objectNumber: String): Flow<ArtObject> {
        return remoteArtObjectDataSource.getArtObject(objectNumber)
            .onEach {
                localArtObjectDataSource.addArtObjects(listOf(it))
            }
    }
}