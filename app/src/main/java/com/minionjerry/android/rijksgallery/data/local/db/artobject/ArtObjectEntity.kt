package com.minionjerry.android.rijksgallery.data.local.db.artobject

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "art_object")
class ArtObjectEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "object_number")val objectNumber: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "artist") val artist: String,
    @Embedded val headerImage: HeaderImageEntity
)

class HeaderImageEntity(
    @ColumnInfo(name = "image_guid") val guid: String,
    @ColumnInfo(name="image_data", typeAffinity = ColumnInfo.BLOB) val image: ByteArray,
)