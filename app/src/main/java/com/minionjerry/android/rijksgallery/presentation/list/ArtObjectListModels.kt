package com.minionjerry.android.rijksgallery.presentation.list

import com.minionjerry.android.rijksgallery.domain.entity.HeaderImage

data class ArtObjectListItemModel(
    val id: String,
    val objectNumber: String,
    val title: String,
    val artist: String,
    val headerImage: HeaderImageModel
)

data class HeaderImageModel(
    val guid: String,
    val offsetX: Int,
    val offsetY: Int,
    val width: Int,
    val height: Int,
    val url: String
)

data class ArtObjectListModel(
    val headerText: String = "",
    val items: List<ArtObjectListItemModel> = listOf()
)

data class GroupedArtObjectListModel(
    val list: List<ArtObjectListModel> = listOf()
)