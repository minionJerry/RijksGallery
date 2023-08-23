package com.minionjerry.android.rijksgallery.data.repository.source.remote

import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import kotlinx.coroutines.flow.Flow

interface RemoteArtObjectDataSource {
    suspend fun getArtObjects(pageNumber: Int): List<ArtObject>
    fun getArtObject(objectNumber: String): Flow<ArtObject>
}