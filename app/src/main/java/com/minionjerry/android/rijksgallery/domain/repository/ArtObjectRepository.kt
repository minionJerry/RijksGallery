package com.minionjerry.android.rijksgallery.domain.repository

import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import kotlinx.coroutines.flow.Flow

interface ArtObjectRepository {
    fun getArtObjects(): Flow<List<ArtObject>>
    fun getArtObject(objectNumber: String): Flow<ArtObject>
}