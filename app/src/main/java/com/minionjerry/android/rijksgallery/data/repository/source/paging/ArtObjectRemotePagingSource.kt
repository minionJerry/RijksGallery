package com.minionjerry.android.rijksgallery.data.repository.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.minionjerry.android.rijksgallery.data.repository.source.remote.RemoteArtObjectDataSource
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import retrofit2.HttpException
import java.io.IOException

const val PAGE_SIZE = 30
const val DEFAULT_INIT_KEY = 0
class ArtObjectPagingSource(
    private val remoteArtObjectDataSource: RemoteArtObjectDataSource
) : PagingSource<Int, ArtObject>(){
    override fun getRefreshKey(state: PagingState<Int, ArtObject>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtObject> {
        return try {
            val currentPage = params.key ?: DEFAULT_INIT_KEY
            val artObjects = remoteArtObjectDataSource.getArtObjects(
                pageNumber = currentPage,
                pageSize = PAGE_SIZE
            )
           LoadResult.Page(
                data = artObjects,
                prevKey = if (currentPage == DEFAULT_INIT_KEY) null else currentPage - 1,
                nextKey = if (artObjects.isEmpty()) null else  currentPage + (params.loadSize / PAGE_SIZE)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}