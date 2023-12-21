package com.azmetov.playlistmaker.features.media.domain

import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.features.player.domain.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCaseImpl(
    private val favoritesRepository: FavoritesRepository
) : GetFavoritesUseCase {

    override fun getFavoritesUseCase(): Flow<List<Track>> =
        favoritesRepository.getFavoritesFlow()

}