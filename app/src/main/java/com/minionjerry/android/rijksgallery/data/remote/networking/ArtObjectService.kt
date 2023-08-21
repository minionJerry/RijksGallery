package com.minionjerry.android.rijksgallery.data.remote.networking

import retrofit2.http.GET
import retrofit2.http.Path

interface ArtObjectService {
    @GET("en/collection")
    suspend fun getArtObjects(): CollectionResponse

    @GET("en/collection/{objectNumber}")
    suspend fun getArtObject(@Path("object-number") objectNumber: String): ArtObjectApiModel
}