package com.azmetov.playlistmaker.core.data.frameworks.storage.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.azmetov.playlistmaker.core.data.frameworks.storage.db.entity.FavoriteTrack
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: FavoriteTrack)

    @Delete
    suspend fun deleteTrack(track: FavoriteTrack)

    @Query("SELECT * FROM favorites_table ORDER BY timeWhenAdded DESC")
    fun getListFlow(): Flow<List<FavoriteTrack>>

    @Query("SELECT trackId FROM favorites_table")
    suspend fun getListId(): List<Int>
}