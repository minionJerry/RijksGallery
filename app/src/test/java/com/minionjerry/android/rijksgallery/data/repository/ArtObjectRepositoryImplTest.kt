package com.minionjerry.android.rijksgallery.data.repository

import com.minionjerry.android.rijksgallery.data.repository.source.local.LocalArtObjectDataSource
import com.minionjerry.android.rijksgallery.data.repository.source.remote.RemoteArtObjectDataSource
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.ArtImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ArtObjectRepositoryImplTest {

    private val remoteArtObjectDataSource = mock<RemoteArtObjectDataSource>()
    private val localArtObjectDataSource = mock<LocalArtObjectDataSource>()
    private val artObjectRepositoryImpl = ArtObjectRepositoryImpl(remoteArtObjectDataSource)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getArtObjects() = runTest {
        val image = ArtImage("29a2a516-f1d2-4713-9cbd-7a4458026057",0,0,1920,460,"https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0")
        val artObject1 = ArtObject("nl-SK-C-5","SK-C-5","De Nachtwacht1","Rembrandt van Rijn",image)
        val artObjects = listOf(artObject1)

        whenever(remoteArtObjectDataSource.getArtObjects()).thenReturn(flowOf(artObjects))

        val result = artObjectRepositoryImpl.getArtObjects().first()
        assertEquals(artObjects, result)
//        verify(localArtObjectDataSource).addArtObjects(artObjects)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getArtObject() = runTest {
        val objectNumber = "SK-C-5"
        val image = ArtImage("29a2a516-f1d2-4713-9cbd-7a4458026057",0,0,1920,460,"https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0")
        val artObject1 = ArtObject("nl-SK-C-5","SK-C-5","De Nachtwacht1","Rembrandt van Rijn", image)

        whenever(remoteArtObjectDataSource.getArtObject(objectNumber)).thenReturn(flowOf(artObject1))

        val result = artObjectRepositoryImpl.getArtObject(objectNumber).first()
        assertEquals(artObject1, result)
//        verify(localArtObjectDataSource).addArtObjects(listOf(artObject1))
    }
}