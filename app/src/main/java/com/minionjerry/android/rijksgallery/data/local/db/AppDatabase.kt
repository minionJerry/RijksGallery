package com.minionjerry.android.rijksgallery.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.minionjerry.android.rijksgallery.data.local.db.artobject.ArtObjectDao
import com.minionjerry.android.rijksgallery.data.local.db.artobject.ArtObjectEntity

@Database(entities = [ArtObjectEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun artObjectDao(): ArtObjectDao
}