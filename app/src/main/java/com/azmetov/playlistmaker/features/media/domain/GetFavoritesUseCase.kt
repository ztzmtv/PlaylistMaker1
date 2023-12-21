package com.azmetov.playlistmaker.features.media.domain

import com.azmetov.playlistmaker.core.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface GetFavoritesUseCase {
    fun getFavoritesUseCase(): Flow<List<Track>>
}