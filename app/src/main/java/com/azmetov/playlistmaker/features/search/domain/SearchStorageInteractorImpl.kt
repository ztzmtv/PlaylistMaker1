package com.azmetov.playlistmaker.features.search.domain

import com.azmetov.playlistmaker.core.domain.models.Track

class SearchStorageInteractorImpl(
    private val searchRepository: SearchRepository
) : SearchStorageInteractor {
    override fun addToSharedPrefsUseCase(track: Track) {
        searchRepository.addToStorage(track)
    }

    override fun getListFromStorageUseCase(): List<Track>? {
        return searchRepository.getListFromStorage()
    }

    override fun clearStorageUseCase() {
        searchRepository.clearStorage()
    }
}