package com.minionjerry.android.rijksgallery.domain.usecase

import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.HeaderImage
import com.minionjerry.android.rijksgallery.domain.repository.ArtObjectRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetArtObjectsGroupedByArtistUseCaseTest{
    private val artObjectRepository = mock<ArtObjectRepository>()
    private val useCase = GetArtObjectsGroupedByArtistUseCase(mock(), artObjectRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testProcess() = runTest {
        val image = HeaderImage("29a2a516-f1d2-4713-9cbd-7a4458026057",0,0,1920,460,"https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0")
        val artObject1 = ArtObject("nl-SK-C-5","SK-C-5","De Nachtwacht1","Rembrandt van Rijn", image)
        val artObject2 = ArtObject("nl-SK-C-6","SK-C-6","De Nachtwacht6","Rembrandt van Rijn", image)
        val artObject3 = ArtObject("nl-SK-C-7","SK-C-7","De Nachtwacht7","Van Gogh", image)
        val listOfAllArtObjects = listOf(artObject1,artObject2,artObject3)

        val expectedResult = listOfAllArtObjects.groupBy { it.artist }

        whenever(artObjectRepository.getArtObjects()).thenReturn(flowOf(listOfAllArtObjects))

        val response = useCase.process(GetArtObjectsGroupedByArtistUseCase.Request).first()
        assertEquals(GetArtObjectsGroupedByArtistUseCase.Response(expectedResult), response)
    }
}