package com.minionjerry.android.rijksgallery.data.repository.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.minionjerry.android.rijksgallery.data.repository.source.remote.RemoteArtObjectDataSource
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import retrofit2.HttpException
import java.io.IOException

const val MAX_PAGE_SIZE = 20
const val PREFETCH_DISTANCE = 20
class ArtObjectPagingSource(
    private val remoteArtObjectDataSource: RemoteArtObjectDataSource
) : PagingSource<Int, ArtObject>(){
    override fun getRefreshKey(state: PagingState<Int, ArtObject>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtObject> {
        return try {
            val currentPage = params.key ?: 1
            val artObjects = remoteArtObjectDataSource.getArtObjects(
                pageNumber = currentPage
            )
            LoadResult.Page(
                data = artObjects,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (artObjects.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}