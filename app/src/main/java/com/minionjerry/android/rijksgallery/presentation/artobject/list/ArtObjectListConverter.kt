package com.minionjerry.android.rijksgallery.presentation.artobject.list

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.Result
import com.minionjerry.android.rijksgallery.domain.usecase.GetArtObjectsUseCase
import javax.inject.Inject

class ArtObjectListConverter @Inject constructor() {

    fun convert(groupedArtObjectListResult: Result<GetArtObjectsUseCase.Response>): PagingData<ArtObjectUiModel> {
        return when (groupedArtObjectListResult) {
            is Result.Success -> {
                groupedArtObjectListResult.data.artObjects.map(::convert)
                    .insertSeparators { artObjectListItemModel: ArtObjectListItemModel?, artObjectListItemModel2: ArtObjectListItemModel? ->
                        if (artObjectListItemModel2 != null && artObjectListItemModel?.artist != artObjectListItemModel2.artist) {
                            HeaderItemModel(artObjectListItemModel2.artist)
                        } else {
                            // no separator - either end of list, or artists are the same
                            null
                        }
                    }
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