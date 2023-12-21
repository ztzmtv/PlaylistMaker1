package com.azmetov.playlistmaker.features.player.domain

import com.azmetov.playlistmaker.core.domain.models.Track

class PlayerStorageInteractorImpl(
    private val playerRepository: PlayerRepository,
    private val favoritesRepository: FavoritesRepository
) : PlayerStorageInteractor {
    override fun saveTrackToStorageUseCase(track: Track) =
        playerRepository.saveTrackToStorage(track)

    override fun loadTrackFromStoreUseCase(): Track? =
        playerRepository.loadTrackFromStore()

    override suspend fun addToFavoritesUseCase(track: Track) =
        favoritesRepository.insertTrack(track)

    override suspend fun removeFromFavoritesUseCase(track: Track) =
        favoritesRepository.deleteTrack(track)

    override suspend fun getFavoritesIdsUseCase(): List<Int> =
        favoritesRepository.getListId()
}