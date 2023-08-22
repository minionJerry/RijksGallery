package com.minionjerry.android.rijksgallery.presentation.artobject.list

import androidx.core.graphics.ColorUtils
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.Result
import com.minionjerry.android.rijksgallery.domain.usecase.GetArtObjectsGroupedByArtistUseCase
import com.minionjerry.android.rijksgallery.presentation.UiState
import javax.inject.Inject

class ArtObjectListConverter @Inject constructor(){

    fun convert(groupedArtObjectListResult: Result<GetArtObjectsGroupedByArtistUseCase.Response>): UiState<GroupedArtObjectListModel> {
        return when (groupedArtObjectListResult) {
            is Result.Success -> {
                val groupedList = groupedArtObjectListResult.data.groupedArtObjects.map { entry ->
                    ArtObjectListModel(
                        headerText = entry.key,
                        items = entry.value.map { convert(it) }
                    )
                }.toList()
                val groupedListItemModel = GroupedArtObjectListModel(groupedList)
                UiState.Success(groupedListItemModel)
            }
            is Result.Error -> {
                UiState.Error(groupedArtObjectListResult.exception.localizedMessage.orEmpty())
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