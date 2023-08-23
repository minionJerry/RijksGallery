package com.minionjerry.android.rijksgallery.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.minionjerry.android.rijksgallery.data.repository.source.paging.ArtObjectPagingSource
import com.minionjerry.android.rijksgallery.data.repository.source.paging.PAGE_SIZE
import com.minionjerry.android.rijksgallery.data.repository.source.remote.RemoteArtObjectDataSource
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.repository.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtObjectRepositoryImpl @Inject constructor(
    private val remoteArtObjectDataSource: RemoteArtObjectDataSource
) : ArtObjectRepository {
    override fun getArtObjects(): Flow<PagingData<ArtObject>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = 10, enablePlaceholders = false),
            pagingSourceFactory = { ArtObjectPagingSource(remoteArtObjectDataSource) }
        ).flow
    }

    override fun getArtObject(objectNumber: String): Flow<ArtObject> {
        return remoteArtObjectDataSource.getArtObject(objectNumber)
    }
}