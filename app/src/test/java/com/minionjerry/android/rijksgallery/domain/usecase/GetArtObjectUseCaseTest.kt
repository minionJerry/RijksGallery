package com.minionjerry.android.rijksgallery.domain.usecase

import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.HeaderImage
import com.minionjerry.android.rijksgallery.domain.repository.ArtObjectRepository
import com.minionjerry.android.rijksgallery.domain.usecase.GetArtObjectUseCase
import com.minionjerry.android.rijksgallery.domain.usecase.GetArtObjectsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetArtObjectUseCaseTest {
    private val artObjectRepository = mock<ArtObjectRepository>()
    private val useCase = GetArtObjectUseCase(mock(), artObjectRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testProcess() = runTest {
        val objectNumber = "SK-C-5"
        val image = HeaderImage("29a2a516-f1d2-4713-9cbd-7a4458026057",0,0,1920,460,"https://lh3.googleusercontent.com/O7ES8hCeygPDvHSob5Yl4bPIRGA58EoCM-ouQYN6CYBw5jlELVqk2tLkHF5C45JJj-5QBqF6cA6zUfS66PUhQamHAw=s0")
        val artObject1 = ArtObject("nl-SK-C-5","SK-C-5","De Nachtwacht1",image)

        whenever(artObjectRepository.getArtObject(objectNumber)).thenReturn(flowOf(artObject1))

        val response = useCase.process(GetArtObjectUseCase.Request(objectNumber)).first()
        assertEquals(GetArtObjectUseCase.Response(artObject1), response)
    }
}