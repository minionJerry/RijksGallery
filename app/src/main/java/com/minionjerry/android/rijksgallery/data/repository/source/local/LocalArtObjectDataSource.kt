package com.minionjerry.android.rijksgallery.data.repository.source.local

import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import kotlinx.coroutines.flow.Flow

interface LocalArtObjectDataSource {
    fun getArtObjects(): Flow<List<ArtObject>>
    suspend fun saveArtObjects(artObjects: List<ArtObject>)
}