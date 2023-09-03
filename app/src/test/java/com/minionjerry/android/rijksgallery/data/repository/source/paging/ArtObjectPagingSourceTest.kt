package com.minionjerry.android.rijksgallery.data.repository.source.paging

import androidx.paging.PagingSource
import com.minionjerry.android.rijksgallery.data.repository.source.remote.RemoteArtObjectDataSource
import com.minionjerry.android.rijksgallery.domain.entity.ArtImage
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.ArtObjectsResponse
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

class ArtObjectPagingSourceTest {

    private val remoteArtObjectDataSource = mock<RemoteArtObjectDataSource>()
    private var artObjectPagingSource: ArtObjectPagingSource = ArtObjectPagingSource(remoteArtObjectDataSource)

    @Test
    fun testGetArtObjectsError() = runTest {
        val error = HttpException(Response.error<ArtObject>(404, "blah".toResponseBody()))

        whenever(remoteArtObjectDataSource.getArtObjects(any(), any())).thenThrow(error)

        val expected = PagingSource.LoadResult.Error<Int, ArtObject>(error)
        val result = artObjectPagingSource.load(PagingSource.LoadParams.Refresh(0,1, false))
        assertEquals(expected, result)
    }

    @Test
    fun testGetArtObjectsNull() = runTest {
        val error = NullPointerException("Cannot invoke \"com.minionjerry.android.rijksgallery.domain.entity.ArtObjectsResponse.getArtObjects()\" because \"artObjectsResponse\" is null")

        whenever(remoteArtObjectDataSource.getArtObjects(any(), any())).thenReturn(null)

        val expected = PagingSource.LoadResult.Error<Int, ArtObject>(error).toString()
        val result = artObjectPagingSource.load(PagingSource.LoadParams.Refresh(0,1,false)).toString()
        assertEquals(expected,result)
    }

    @Test
    fun testGetArtObjectsRefresh() = runTest {
        val image = ArtImage("29a2a516-f1d2-4713-9cbd-7a4458026057",0,0,1920,460,"https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0")
        val artObject1 = ArtObject("nl-SK-C-5","SK-C-5","De Nachtwacht1","Rembrandt van Rijn",image)
        val listOfObjects = listOf(artObject1)
        val response = ArtObjectsResponse(listOfObjects, 1)

        whenever(remoteArtObjectDataSource.getArtObjects(any(), any())).thenReturn(response)

        val expected = PagingSource.LoadResult.Page<Int, ArtObject>(listOfObjects, null, 1)
        val result = artObjectPagingSource.load(PagingSource.LoadParams.Refresh(0,1,false))
        assertEquals(expected, result)
    }

    @Test
    fun testGetArtObjectsAppend() = runTest {
        val image = ArtImage("29a2a516-f1d2-4713-9cbd-7a4458026057",0,0,1920,460,"https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0")
        val artObject1 = ArtObject("nl-SK-C-5","SK-C-5","De Nachtwacht1","Rembrandt van Rijn",image)
        val listOfObjects = listOf(artObject1)
        val response = ArtObjectsResponse(listOfObjects, 1)

        whenever(remoteArtObjectDataSource.getArtObjects(any(), any())).thenReturn(response)

        val expected = PagingSource.LoadResult.Page<Int, ArtObject>(listOfObjects, 0, 2)
        val result = artObjectPagingSource.load(PagingSource.LoadParams.Append(1,1,false))
        assertEquals(expected, result)
    }

    @Test
    fun testGetArtObjectsPrepend() = runTest {
        val image = ArtImage("29a2a516-f1d2-4713-9cbd-7a4458026057",0,0,1920,460,"https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0")
        val artObject1 = ArtObject("nl-SK-C-5","SK-C-5","De Nachtwacht1","Rembrandt van Rijn",image)
        val listOfObjects = listOf(artObject1)
        val response = ArtObjectsResponse(listOfObjects, 1)

        whenever(remoteArtObjectDataSource.getArtObjects(any(), any())).thenReturn(response)

        val expected = PagingSource.LoadResult.Page<Int, ArtObject>(listOfObjects, null, 1)
        val result = artObjectPagingSource.load(PagingSource.LoadParams.Prepend(0,1,false))
        assertEquals(expected, result)
    }

}