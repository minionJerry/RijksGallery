package com.minionjerry.android.rijksgallery.presentation.artobject.list

data class GroupedArtObjectListModel(
    val list: List<ArtObjectListModel> = listOf()
)

data class ArtObjectListModel(
    val headerText: String = "",
    val items: List<ArtObjectListItemModel> = listOf()
)

data class ArtObjectListItemModel(
    val id: String,
    val objectNumber: String,
    val title: String,
    val artist: String,
    val image: ArtImageModel
)

data class ArtImageModel(
    val guid: String,
    val offsetX: Int,
    val offsetY: Int,
    val width: Int,
    val height: Int,
    val url: String
)

