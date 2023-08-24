package com.minionjerry.android.rijksgallery.data.remote.source

import com.minionjerry.android.rijksgallery.data.remote.networking.ArtObjectApiModel
import com.minionjerry.android.rijksgallery.data.remote.networking.ArtObjectService
import com.minionjerry.android.rijksgallery.data.repository.source.remote.RemoteArtObjectDataSource
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.ArtImage
import com.minionjerry.android.rijksgallery.domain.entity.UseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteArtObjectDataSourceImpl @Inject constructor(
    private val artObjectService: ArtObjectService
) : RemoteArtObjectDataSource {

    override suspend fun getArtObjects(pageNumber: Int, pageSize: Int): List<ArtObject> {
        val networkArtObject = artObjectService.getArtObjects(pageNumber, pageSize).artObjects
        return networkArtObject.map(::convert)
    }


    override fun getArtObject(objectNumber: String): Flow<ArtObject> {
        return flow {
            emit(artObjectService.getArtObject(objectNumber).artObject)
        }.map {
            convert(it)
        }.catch {
            throw UseCaseException.ArtObjectException(it)
        }
    }

    private fun convert(artObjectApiModel: ArtObjectApiModel): ArtObject =
        ArtObject(
            artObjectApiModel.id,
            artObjectApiModel.objectNumber,
            artObjectApiModel.title,
            artObjectApiModel.artist,
            with(artObjectApiModel.headerImage) {
                ArtImage(
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