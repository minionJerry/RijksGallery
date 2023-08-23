package com.minionjerry.android.rijksgallery.data.remote.networking

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtObjectService {
    @GET("en/collection")
    suspend fun getArtObjects(@Query("p") pageNumber: Int, @Query("ps") pageSize: Int): CollectionResponse

    @GET("en/collection/{objectNumber}")
    suspend fun getArtObject(@Path("object-number") objectNumber: String): ArtObjectApiModel
}