package com.azmetov.playlistmaker.features.player.domain

import com.azmetov.playlistmaker.core.domain.models.PlayerState
import com.azmetov.playlistmaker.core.domain.models.Track
import kotlinx.coroutines.flow.StateFlow

interface PlayerInteractor {
    val playerFlow: StateFlow<PlayerState>
    fun prepareUseCase(url: String)
    fun playUseCase()
    fun pauseUseCase()
    fun getPositionUseCase(): Int
    fun releaseUseCase()
}