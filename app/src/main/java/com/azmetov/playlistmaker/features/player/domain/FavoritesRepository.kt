package com.azmetov.playlistmaker.features.player.domain

import com.azmetov.playlistmaker.core.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun insertTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    fun getFavoritesFlow(): Flow<List<Track>>
    suspend fun getListId(): List<Int>
}