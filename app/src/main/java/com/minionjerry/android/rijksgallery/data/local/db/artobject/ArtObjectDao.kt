package com.minionjerry.android.rijksgallery.data.local.db.artobject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtObjectDao {

    @Query("SELECT * from art_object")
    fun getArtObjects(): Flow<List<ArtObjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArtObjets(artObjects: List<ArtObjectEntity>)
}