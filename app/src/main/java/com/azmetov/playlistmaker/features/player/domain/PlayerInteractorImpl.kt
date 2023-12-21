package com.azmetov.playlistmaker.features.player.domain

import com.azmetov.playlistmaker.core.domain.models.PlayerState
import com.azmetov.playlistmaker.core.domain.models.Track
import kotlinx.coroutines.flow.StateFlow

class PlayerInteractorImpl(
    private val repository: PlayerRepository,
) : PlayerInteractor {
    override val playerFlow: StateFlow<PlayerState> = repository.playerFlow
    override fun prepareUseCase(url: String) = repository.prepare(url)
    override fun playUseCase() = repository.play()
    override fun pauseUseCase() = repository.pause()
    override fun getPositionUseCase(): Int = repository.getPosition()
    override fun releaseUseCase() = repository.release()
}