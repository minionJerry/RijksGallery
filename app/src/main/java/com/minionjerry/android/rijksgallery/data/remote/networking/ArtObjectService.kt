package com.minionjerry.android.rijksgallery.data.remote.networking

import retrofit2.http.GET
import retrofit2.http.Path

interface ArtObjectService {
    @GET("/collection")
    suspend fun getArtObjects(): List<ArtObjectApiModel>

    @GET("/collection/{objectNumber}")
    suspend fun getArtObject(@Path("object-number") objectNumber: String): ArtObjectApiModel
}