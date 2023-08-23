package com.minionjerry.android.rijksgallery.presentation.artobject.list


open class ArtObjectUiModel
data class ArtObjectListItemModel(
    val id: String,
    val objectNumber: String,
    val title: String,
    val artist: String,
    val image: ArtImageModel
) : ArtObjectUiModel()
data class HeaderItemModel(val artist: String): ArtObjectUiModel()

data class ArtImageModel(
    val guid: String,
    val offsetX: Int,
    val offsetY: Int,
    val width: Int,
    val height: Int,
    val url: String
)

