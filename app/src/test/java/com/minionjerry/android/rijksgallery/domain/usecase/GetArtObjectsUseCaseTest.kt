package com.minionjerry.android.rijksgallery.domain.usecase

import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.ArtImage
import com.minionjerry.android.rijksgallery.domain.repository.ArtObjectRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetArtObjectsUseCaseTest {
    private val artObjectRepository = mock<ArtObjectRepository>()
    private val useCase = GetArtObjectsUseCase(mock(), artObjectRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testProcess() = runTest {
        val image = ArtImage("29a2a516-f1d2-4713-9cbd-7a4458026057",0,0,1920,460,"https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0")
        val artObject1 = ArtObject("nl-SK-C-5","SK-C-5","De Nachtwacht1","Rembrandt van Rijn", image)
        val artObject2 = ArtObject("nl-SK-C-6","SK-C-6","De Nachtwacht2","Rembrandt van Rijn", image)
        val artObject3 = ArtObject("nl-SK-C-7","SK-C-7","De Nachtwacht3","Rembrandt van Rijn", image)

        whenever(artObjectRepository.getArtObjects()).thenReturn(flowOf(listOf( artObject1,artObject2,artObject3)))

        val response = useCase.process(GetArtObjectsUseCase.Request).first()
        assertEquals(GetArtObjectsUseCase.Response(listOf(artObject1, artObject2, artObject3)), response)
    }
}