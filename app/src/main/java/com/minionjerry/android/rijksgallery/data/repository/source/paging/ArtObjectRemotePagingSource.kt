package com.minionjerry.android.rijksgallery.data.repository.source.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.minionjerry.android.rijksgallery.data.repository.source.remote.RemoteArtObjectDataSource
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject

const val PAGE_SIZE = 30
const val DEFAULT_INIT_KEY = 0
class ArtObjectPagingSource(
    private val remoteArtObjectDataSource: RemoteArtObjectDataSource
) : PagingSource<Int, ArtObject>(){

    override fun getRefreshKey(state: PagingState<Int, ArtObject>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtObject> {
        return try {
            val currentPage = params.key ?: DEFAULT_INIT_KEY
            val artObjectsResponse = remoteArtObjectDataSource.getArtObjects(
                pageNumber = currentPage,
                pageSize = PAGE_SIZE
            )

           val page = LoadResult.Page(
                data = artObjectsResponse.artObjects,
                prevKey = if (currentPage == DEFAULT_INIT_KEY) null else currentPage - 1,
                nextKey = if (artObjectsResponse.artObjects.isEmpty()) null else currentPage + 1
            )
            page
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}