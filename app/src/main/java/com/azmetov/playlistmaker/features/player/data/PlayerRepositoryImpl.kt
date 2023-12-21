package com.azmetov.playlistmaker.features.player.data

import com.azmetov.playlistmaker.core.data.frameworks.storage.Storage
import com.azmetov.playlistmaker.core.domain.models.PlayerState
import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.features.player.data.player.PlayerManager
import com.azmetov.playlistmaker.features.player.domain.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayerRepositoryImpl(
    private var playerManager: PlayerManager? = null,
    private val storage: Storage
) : PlayerRepository {
    override val playerFlow: StateFlow<PlayerState> =
        playerManager?.playerFlow ?: MutableStateFlow(PlayerState.INIT)

    override fun prepare(url: String) {
        playerManager?.prepare(url)
    }

    override fun play() {
        playerManager?.play()
    }

    override fun pause() {
        playerManager?.pause()
    }

    override fun getPosition(): Int {
        return playerManager?.getPosition() ?: 0
    }

    override fun release() {
        playerManager?.release()
        if (playerManager != null) {
            playerManager = null
        }
    }

    override fun saveTrackToStorage(track: Track) {
        storage.saveSingleTrack(track)
    }

    override fun loadTrackFromStore(): Track? {
        return storage.loadSingleTrack()
    }

}