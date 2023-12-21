package com.azmetov.playlistmaker.features.player.data.player

import com.azmetov.playlistmaker.core.domain.models.PlayerState
import kotlinx.coroutines.flow.StateFlow

interface PlayerManager {
    val playerFlow: StateFlow<PlayerState>
    fun prepare(url: String)
    fun play()
    fun pause()
    fun getPosition(): Int
    fun release()
}