package com.minionjerry.android.rijksgallery.data.remote.source

import com.minionjerry.android.rijksgallery.data.remote.networking.ArtImageApiModel
import com.minionjerry.android.rijksgallery.data.remote.networking.ArtObjectApiModel
import com.minionjerry.android.rijksgallery.data.remote.networking.ArtObjectService
import com.minionjerry.android.rijksgallery.data.remote.networking.CollectionResponse
import com.minionjerry.android.rijksgallery.data.remote.networking.DetailResponse
import com.minionjerry.android.rijksgallery.data.repository.source.paging.PAGE_SIZE
import com.minionjerry.android.rijksgallery.domain.entity.ArtImage
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.ArtObjectsResponse
import com.minionjerry.android.rijksgallery.domain.entity.UseCaseException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RemoteArtObjectDataSourceImplTest {

    private val artObjectService = mock<ArtObjectService>()
    private val remoteArtObjectDataSourceImpl = RemoteArtObjectDataSourceImpl(artObjectService)

    @Test
    fun testGetArtObjects() = runTest {
        val image = ArtImage(
            "29a2a516-f1d2-4713-9cbd-7a4458026057",
            0,
            0,
            1920,
            460,
            "https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0"
        )
        val artObject =
            ArtObject("nl-SK-C-5", "SK-C-5", "De Nachtwacht1", "Rembrandt van Rijn", image)
        val expectedResponse = ArtObjectsResponse(listOf(artObject), 1)

        val remoteArtImageApiModel = ArtImageApiModel(
            "29a2a516-f1d2-4713-9cbd-7a4458026057",
            0,
            0,
            1920,
            460,
            "https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0"
        )
        val remoteArtObjectApiModel = ArtObjectApiModel(
            "nl-SK-C-5",
            "SK-C-5",
            "De Nachtwacht1",
            "Rembrandt van Rijn",
            remoteArtImageApiModel
        )
        val collectionResponse = CollectionResponse(listOf(remoteArtObjectApiModel), 1)

        whenever(artObjectService.getArtObjects(any(), any())).thenReturn(collectionResponse)

        val result = remoteArtObjectDataSourceImpl.getArtObjects(any(), any())
        assertEquals(expectedResponse, result)
    }

    @Test
    fun testGetArtObject() = runTest {
        val objectNumber = "SK-C-5"
        val image = ArtImage(
            "29a2a516-f1d2-4713-9cbd-7a4458026057",
            0,
            0,
            1920,
            460,
            "https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0"
        )
        val expectedArtObject =
            ArtObject("nl-SK-C-5", "SK-C-5", "De Nachtwacht1", "Rembrandt van Rijn", image)
        val remoteArtImageApiModel = ArtImageApiModel(
            "29a2a516-f1d2-4713-9cbd-7a4458026057",
            0,
            0,
            1920,
            460,
            "https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0"
        )
        val remoteArtObjectApiModel = ArtObjectApiModel(
            "nl-SK-C-5",
            "SK-C-5",
            "De Nachtwacht1",
            "Rembrandt van Rijn",
            remoteArtImageApiModel
        )

        val response = DetailResponse(remoteArtObjectApiModel)

        whenever(artObjectService.getArtObject(objectNumber)).thenReturn(response)

        val result = remoteArtObjectDataSourceImpl.getArtObject(objectNumber).first()
        assertEquals(expectedArtObject, result)
    }

    @Test
    fun testGetArtObjectThrowsError() = runTest {
        val objectNumber = "SK-C-5"
        whenever(artObjectService.getArtObject(objectNumber)).thenThrow(RuntimeException())
        remoteArtObjectDataSourceImpl.getArtObject(objectNumber).catch {
            assertTrue(it is UseCaseException.ArtObjectException)
        }. collect()
    }
}