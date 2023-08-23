package com.minionjerry.android.rijksgallery.presentation.artobject.list

import androidx.paging.PagingData
import androidx.paging.map
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.Result
import com.minionjerry.android.rijksgallery.domain.usecase.GetArtObjectsUseCase
import javax.inject.Inject

class ArtObjectListConverter @Inject constructor() {

    fun convert(groupedArtObjectListResult: Result<GetArtObjectsUseCase.Response>): PagingData<ArtObjectListItemModel> {
        return when (groupedArtObjectListResult) {
            is Result.Success -> {
                groupedArtObjectListResult.data.artObjects.map(::convert)
            }

            is Result.Error -> {
                PagingData.empty()
            }
        }
    }

    private fun convert(entity: ArtObject) =
        with(entity) {
            ArtObjectListItemModel(
                id,
                objectNumber,
                title,
                artist,
                with(artImage) {
                    ArtImageModel(
                        this.guid,
                        this.offsetX,
                        this.offsetY,
                        this.width,
                        this.height,
                        this.url
                    )
                }
            )
        }
}