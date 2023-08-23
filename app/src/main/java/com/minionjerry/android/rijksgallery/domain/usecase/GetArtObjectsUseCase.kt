package com.minionjerry.android.rijksgallery.domain.usecase

import androidx.paging.PagingData
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.repository.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetArtObjectsUseCase @Inject constructor(
    configuration: Configuration,
    private val artObjectRepository: ArtObjectRepository):
    UseCase<GetArtObjectsUseCase.Request, GetArtObjectsUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<UseCase.Response> {
        return artObjectRepository.getArtObjects()
            .map {
                Response(it)
            }
    }
    object Request: UseCase.Request

    data class Response(val artObjects: PagingData<ArtObject>): UseCase.Response

}