package com.azmetov.playlistmaker.features.player.domain

import com.azmetov.playlistmaker.core.domain.models.Track

interface PlayerStorageInteractor {
    fun saveTrackToStorageUseCase(track: Track)
    fun loadTrackFromStoreUseCase(): Track?

    suspend fun addToFavoritesUseCase(track: Track)
    suspend fun removeFromFavoritesUseCase(track: Track)
    suspend fun getFavoritesIdsUseCase(): List<Int>
}