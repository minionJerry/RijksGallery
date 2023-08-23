package com.minionjerry.android.rijksgallery.domain.repository

import androidx.paging.PagingData
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import kotlinx.coroutines.flow.Flow

interface ArtObjectRepository {
    fun getArtObjects(): Flow<PagingData<ArtObject>>
    fun getArtObject(objectNumber: String): Flow<ArtObject>
}