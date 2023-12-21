package com.azmetov.playlistmaker.features.search.domain

import com.azmetov.playlistmaker.core.domain.models.NetworkResult
import com.azmetov.playlistmaker.core.domain.models.Track

interface SearchRepository {
    //Network
    suspend fun sendRequest(text: String): NetworkResult

    //Storage
    fun addToStorage(track: Track)
    fun getListFromStorage(): List<Track>?
    fun clearStorage()
    fun saveTrackToStorage(track: Track)
    fun loadTrackFromStorage(): Track?
}