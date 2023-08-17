package com.minionjerry.android.rijksgallery.data.remote.source

import com.minionjerry.android.rijksgallery.data.remote.networking.ArtObjectApiModel
import com.minionjerry.android.rijksgallery.data.remote.networking.ArtObjectService
import com.minionjerry.android.rijksgallery.data.repository.source.remote.RemoteArtObjectDataSource
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import com.minionjerry.android.rijksgallery.domain.entity.HeaderImage
import com.minionjerry.android.rijksgallery.domain.entity.UseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteArtObjectDataSourceImpl @Inject constructor(
    private val artObjectService: ArtObjectService
) : RemoteArtObjectDataSource {
    override fun getArtObjects(): Flow<List<ArtObject>> {
        return flow {
            emit(artObjectService.getArtObjects())
        }.map { list ->
            list.map { artObjectApiModel ->
                convert(artObjectApiModel)
            }
        }.catch {
            throw UseCaseException.ArtObjectException(it)
        }
    }

    override fun getArtObject(objectNumber: String): Flow<ArtObject> {
        return flow {
            emit(artObjectService.getArtObject(objectNumber))
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
                HeaderImage(
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