package com.minionjerry.android.rijksgallery.data.remote.networking

import com.squareup.moshi.Json

data class CollectionResponse(
    @Json(name = "artObjects") val artObjects: List<ArtObjectApiModel>,
    @Json(name="count") val count: Int
)

data class DetailResponse(
    @Json(name = "artObject") val artObject: ArtObjectApiModel
)

data class ArtObjectApiModel(
    @Json(name = "id") val id: String,
    @Json(name = "objectNumber") val objectNumber: String,
    @Json(name = "title") val title: String,
    @Json(name = "principalOrFirstMaker") val artist: String,
    @Json(name = "webImage") val headerImage: ArtImageApiModel
)


data class ArtImageApiModel(
    @Json(name = "guid") val guid: String,
    @Json(name = "offsetPercentageX") val offsetX: Int,
    @Json(name = "offsetPercentageY") val offsetY: Int,
    @Json(name = "width") val width: Int,
    @Json(name = "height") val height: Int,
    @Json(name = "url") val url: String
)
