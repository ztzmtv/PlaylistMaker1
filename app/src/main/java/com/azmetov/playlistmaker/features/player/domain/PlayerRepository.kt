package com.azmetov.playlistmaker.features.player.domain

import com.azmetov.playlistmaker.core.domain.models.PlayerState
import com.azmetov.playlistmaker.core.domain.models.Track
import kotlinx.coroutines.flow.StateFlow

interface PlayerRepository {
    val playerFlow: StateFlow<PlayerState>
    fun prepare(url: String)
    fun play()
    fun pause()
    fun getPosition(): Int
    fun release()
    fun saveTrackToStorage(track: Track)
    fun loadTrackFromStore(): Track?
}