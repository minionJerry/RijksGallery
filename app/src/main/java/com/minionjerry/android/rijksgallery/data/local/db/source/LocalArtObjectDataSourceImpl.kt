package com.minionjerry.android.rijksgallery.data.local.db.source

import com.minionjerry.android.rijksgallery.data.local.db.artobject.ArtObjectDao
import com.minionjerry.android.rijksgallery.data.repository.source.local.LocalArtObjectDataSource
import com.minionjerry.android.rijksgallery.domain.entity.ArtObject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalArtObjectDataSourceImpl @Inject constructor(
    private val artObjectDao: ArtObjectDao
) : LocalArtObjectDataSource {
    override fun getArtObjects(): Flow<List<ArtObject>> {
        TODO("Not yet implemented")
    }

    override suspend fun addArtObjects(artObjects: List<ArtObject>) {
        TODO("Not yet implemented")
    }
}