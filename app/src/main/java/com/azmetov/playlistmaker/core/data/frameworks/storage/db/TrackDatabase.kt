package com.azmetov.playlistmaker.core.data.frameworks.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azmetov.playlistmaker.core.data.frameworks.storage.db.entity.FavoriteTrack

@Database(
    entities = [FavoriteTrack::class],
    version = 2
)

abstract class TrackDatabase : RoomDatabase() {
    abstract fun getDao(): FavoritesDao
}