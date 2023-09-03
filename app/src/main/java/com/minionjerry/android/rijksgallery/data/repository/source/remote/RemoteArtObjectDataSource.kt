package com.minionjerry.android.rijksgallery.data.repository.source.remote

import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.ArtObjectsResponse
import kotlinx.coroutines.flow.Flow

interface RemoteArtObjectDataSource {
    suspend fun getArtObjects(pageNumber: Int, pageSize: Int): ArtObjectsResponse
    fun getArtObject(objectNumber: String): Flow<ArtObject>
}