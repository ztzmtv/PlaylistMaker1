package com.azmetov.playlistmaker.features.player.data.player

import android.media.MediaPlayer
import com.azmetov.playlistmaker.core.domain.models.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayerManagerImpl(
    private val mediaPlayer: MediaPlayer
) : PlayerManager {

    private var playerMutableStateFlow: MutableStateFlow<PlayerState> =
        MutableStateFlow(PlayerState.INIT)
    override val playerFlow: StateFlow<PlayerState> =
        playerMutableStateFlow.asStateFlow()

    override fun prepare(url: String) {
        with(mediaPlayer) {
            reset()
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { playerMutableStateFlow.update { PlayerState.PREPARED } }
            setOnCompletionListener { playerMutableStateFlow.update { PlayerState.PREPARED } }
        }
    }

    override fun play() {
        if (playerMutableStateFlow.value == PlayerState.PAUSED || playerMutableStateFlow.value == PlayerState.PREPARED) {
            mediaPlayer.start()
            playerMutableStateFlow.update { PlayerState.STARTED }
        }
    }

    override fun pause() {
        if (playerMutableStateFlow.value == PlayerState.STARTED) {
            mediaPlayer.pause()
            playerMutableStateFlow.update { PlayerState.PAUSED }
        }
    }

    override fun getPosition(): Int {
        return when (playerMutableStateFlow.value) {
            PlayerState.STARTED -> mediaPlayer.currentPosition
            else -> START_POSITION
        }
    }

    override fun release() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    companion object {
        const val START_POSITION = 0
    }
}
