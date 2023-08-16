package com.minionjerry.android.rijksgallery.domain.usecase

import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.repository.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetArtObjectUseCase @Inject constructor(
    configuration: Configuration,
    private val artObjectRepository: ArtObjectRepository):
    UseCase<GetArtObjectUseCase.Request, GetArtObjectUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<UseCase.Response> {
        return artObjectRepository.getArtObject(request.objectNumber)
            .map {
                Response(it)
            }
    }
    data class Request(val objectNumber: String): UseCase.Request

    data class Response(val artObject: ArtObject): UseCase.Response

}