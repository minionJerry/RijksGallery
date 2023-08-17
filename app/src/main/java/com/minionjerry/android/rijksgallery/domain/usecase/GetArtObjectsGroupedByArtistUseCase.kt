package com.minionjerry.android.rijksgallery.domain.usecase

import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.repository.ArtObjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetArtObjectsGroupedByArtistUseCase @Inject constructor(
    configuration: Configuration,
    private val artObjectRepository: ArtObjectRepository):
    UseCase<GetArtObjectsGroupedByArtistUseCase.Request, GetArtObjectsGroupedByArtistUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<UseCase.Response> {
        return artObjectRepository.getArtObjects()
            .map { list ->
                val groupedArtObjects = list.groupBy { it.artist }
                Response(groupedArtObjects)
            }
    }
    object Request: UseCase.Request

    data class Response(val groupedArtObjects: Map<String, List<ArtObject>>): UseCase.Response

}