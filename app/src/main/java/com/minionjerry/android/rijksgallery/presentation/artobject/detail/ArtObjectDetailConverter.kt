package com.minionjerry.android.rijksgallery.presentation.artobject.detail

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.Result
import com.minionjerry.android.rijksgallery.domain.usecase.GetArtObjectUseCase
import com.minionjerry.android.rijksgallery.domain.usecase.GetArtObjectsUseCase
import com.minionjerry.android.rijksgallery.presentation.UiState
import com.minionjerry.android.rijksgallery.presentation.artobject.list.ArtImageModel
import com.minionjerry.android.rijksgallery.presentation.artobject.list.ArtObjectListItemModel
import javax.inject.Inject

class ArtObjectDetailConverter @Inject constructor() {

    fun convert(artObjectResponse: Result<GetArtObjectUseCase.Response>): UiState<ArtObjectListItemModel> {
        return when (artObjectResponse) {
            is Result.Success -> {
                UiState.Success(convert(artObjectResponse.data.artObject))
            }
            is Result.Error -> {
                UiState.Error(artObjectResponse.exception.localizedMessage.orEmpty())
            }
        }
    }

    fun convert(entity: ArtObject) =
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