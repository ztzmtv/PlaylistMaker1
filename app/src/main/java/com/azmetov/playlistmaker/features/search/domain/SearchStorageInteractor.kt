package com.azmetov.playlistmaker.features.search.domain

import com.azmetov.playlistmaker.core.domain.models.Track

interface SearchStorageInteractor {
    fun addToSharedPrefsUseCase(track: Track)
    fun getListFromStorageUseCase(): List<Track>?
    fun clearStorageUseCase()
}