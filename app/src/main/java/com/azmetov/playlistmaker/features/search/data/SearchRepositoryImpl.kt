package com.azmetov.playlistmaker.features.search.data

import com.azmetov.playlistmaker.core.data.frameworks.network.NetworkClient
import com.azmetov.playlistmaker.core.data.frameworks.storage.Storage
import com.azmetov.playlistmaker.core.domain.models.NetworkResult
import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.features.search.domain.SearchRepository

class SearchRepositoryImpl(
    private val networkHandler: NetworkClient,
    private val storage: Storage
) : SearchRepository {

    override suspend fun sendRequest(text: String): NetworkResult {
     return networkHandler.sendRequest(text)
    }

    override fun addToStorage(track: Track) {
        storage.addTrackToList(track)
    }

    override fun getListFromStorage(): List<Track>? {
        return storage.getTrackList()
    }

    override fun clearStorage() {
        storage.clearTrackList()
    }

    override fun saveTrackToStorage(track: Track) {
        storage.saveSingleTrack(track)
    }

    override fun loadTrackFromStorage(): Track? {
        return storage.loadSingleTrack()
    }
}