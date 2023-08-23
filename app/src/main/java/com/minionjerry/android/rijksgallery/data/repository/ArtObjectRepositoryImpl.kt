package com.minionjerry.android.rijksgallery.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.minionjerry.android.rijksgallery.data.repository.source.paging.ArtObjectPagingSource
import com.minionjerry.android.rijksgallery.data.repository.source.paging.MAX_PAGE_SIZE
import com.minionjerry.android.rijksgallery.data.repository.source.paging.PREFETCH_DISTANCE
import com.minionjerry.android.rijksgallery.data.repository.source.remote.RemoteArtObjectDataSource
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.repository.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtObjectRepositoryImpl @Inject constructor(
    private val remoteArtObjectDataSource: RemoteArtObjectDataSource
) : ArtObjectRepository {
    override fun getArtObjects(): Flow<PagingData<ArtObject>> {
        return Pager(
            config = PagingConfig(pageSize = MAX_PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = { ArtObjectPagingSource(remoteArtObjectDataSource) }
        ).flow
    }

    override fun getArtObject(objectNumber: String): Flow<ArtObject> {
        return remoteArtObjectDataSource.getArtObject(objectNumber)
    }
}