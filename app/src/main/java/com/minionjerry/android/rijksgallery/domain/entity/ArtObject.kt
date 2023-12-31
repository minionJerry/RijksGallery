package com.minionjerry.android.rijksgallery.domain.entity

data class ArtObject(
    val id: String,
    val objectNumber: String,
    val title: String,
    val artist: String,
    val artImage: ArtImage
)

data class ArtImage(
    val guid: String,
    val offsetX: Int,
    val offsetY: Int,
    val width: Int,
    val height: Int,
    val url: String
)
