package com.minionjerry.android.rijksgallery.data.repository

import androidx.paging.PagingData
import com.minionjerry.android.rijksgallery.data.repository.source.paging.ArtObjectPagingSource
import com.minionjerry.android.rijksgallery.data.repository.source.paging.PAGE_SIZE
import com.minionjerry.android.rijksgallery.data.repository.source.remote.RemoteArtObjectDataSource
import com.minionjerry.android.rijksgallery.domain.entity.ArtImage
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ArtObjectRepositoryImplTest {

    private val remoteArtObjectPagingSource = mock<ArtObjectPagingSource>()
    private val remoteArtObjectDataSource = mock<RemoteArtObjectDataSource>()
    private val artObjectRepositoryImpl = ArtObjectRepositoryImpl(remoteArtObjectDataSource)

    private val page = 1
    private val pageSize = PAGE_SIZE

    @Test
    fun getArtObjects() = runTest {
        val image = ArtImage("29a2a516-f1d2-4713-9cbd-7a4458026057",0,0,1920,460,"https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0")
        val artObject1 = ArtObject("nl-SK-C-5","SK-C-5","De Nachtwacht1","Rembrandt van Rijn",image)
        val artObjects = listOf(artObject1)
        val pagingData = PagingData.from(artObjects)

        whenever(remoteArtObjectDataSource.getArtObjects(page, pageSize)).thenReturn(artObjects)

        val result = artObjectRepositoryImpl.getArtObjects().first()
        assertEquals(pagingData, result)
    }

    @Test
    fun getArtObject() = runTest {
        val objectNumber = "SK-C-5"
        val image = ArtImage("29a2a516-f1d2-4713-9cbd-7a4458026057",0,0,1920,460,"https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0")
        val artObject1 = ArtObject("nl-SK-C-5","SK-C-5","De Nachtwacht1","Rembrandt van Rijn", image)

        whenever(remoteArtObjectDataSource.getArtObject(objectNumber)).thenReturn(flowOf(artObject1))

        val result = artObjectRepositoryImpl.getArtObject(objectNumber).first()
        assertEquals(artObject1, result)
    }
}