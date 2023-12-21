package com.azmetov.playlistmaker.features.player.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azmetov.playlistmaker.core.domain.models.PlayerState
import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.features.player.domain.PlayerInteractor
import com.azmetov.playlistmaker.features.player.domain.PlayerStorageInteractor
import com.azmetov.playlistmaker.utils.debounce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val playerStorageInteractor: PlayerStorageInteractor,
    private val playerInteractor: PlayerInteractor,
) : ViewModel() {
    private val isPlayingMutable = MutableLiveData(false)
    private val isFavoriteMutable = MutableLiveData(false)
    private val isInPlaylistMutable = MutableLiveData(false)
    private val trackPositionMutable = MutableLiveData(0)
    fun isPlaying(): LiveData<Boolean> = isPlayingMutable
    fun isFavorite(): LiveData<Boolean> = isFavoriteMutable
    fun isInPlaylist(): LiveData<Boolean> = isInPlaylistMutable
    fun trackPosition(): LiveData<Int> = trackPositionMutable

    private var track: Track? = null
    private var timerJob: Job? = null

    init {
        viewModelScope.launch {
            playerInteractor.playerFlow.collectLatest() { playerState ->
                Log.d("playerFlowViewModelTAG", "$playerState")
                when (playerState) {
                    PlayerState.INIT -> Unit
                    PlayerState.PAUSED -> timerJob?.cancel()
                    PlayerState.STARTED -> timerJob = launch { updatePosition() }
                    PlayerState.PREPARED -> {
                        isPlayingMutable.value = false
                        timerJob?.cancel()
                        updatePosition()
                    }
                }
            }
        }
    }

    fun initTrack(track: Track?) {
        if (track != null) {
            this.track = track
            playerStorageInteractor.saveTrackToStorageUseCase(track)
            viewModelScope.launch(Dispatchers.IO) {
                val favoriteIds = playerStorageInteractor.getFavoritesIdsUseCase()
                if (favoriteIds.contains(track.trackId)) {
                    isFavoriteMutable.postValue(true)
                }
            }
        } else {
            this.track = playerStorageInteractor.loadTrackFromStoreUseCase()
                ?: throw NullPointerException("Extra serializable in SharedPrefs can not be null!")
        }

        track?.previewUrl?.let {
            playerInteractor.prepareUseCase(it)
        }
    }

    fun getPlayClickListener(): () -> Unit = {
        debounce<Unit>(CLICK_PLAY_DEBOUNCE_DELAY, viewModelScope, false) {
            val playerState = playerInteractor.playerFlow.value
            if (playerState == PlayerState.PAUSED || playerState == PlayerState.PREPARED) {
                isPlayingMutable.value = true
                playerInteractor.playUseCase()
            } else if (playerState == PlayerState.STARTED) {
                isPlayingMutable.value = false
                playerInteractor.pauseUseCase()
            }
        }.invoke(Unit)
    }


    fun getFavoriteClickListener(): () -> Unit = convertCallback(
        debounce(CLICK_PLAY_DEBOUNCE_DELAY, viewModelScope, false) {
            isFavoriteMutable.value?.let {
                viewModelScope.launch(Dispatchers.IO) {
                    if (isFavoriteMutable.value!!) {
                        isFavoriteMutable.postValue(false)
                        playerStorageInteractor.removeFromFavoritesUseCase(this@PlayerViewModel.track!!)
                    } else {
                        isFavoriteMutable.postValue(true)
                        playerStorageInteractor.addToFavoritesUseCase(this@PlayerViewModel.track!!)
                    }
                }
            }
        })

    fun getAppPlaylistClickListener(): () -> Unit = {
        isInPlaylistMutable.value = isInPlaylistMutable.value?.not()
    }

    private suspend fun updatePosition() {
        while (true) {
            trackPositionMutable.value = playerInteractor.getPositionUseCase()
            delay(TIMER_DELAY)
        }
    }

    private fun convertCallback(callback: (Unit) -> Unit): () -> Unit {
        return {
            callback(Unit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        playerInteractor.releaseUseCase()
        timerJob?.cancel()
        timerJob = null
    }

    companion object {
        private const val CLICK_PLAY_DEBOUNCE_DELAY = 100L
        private const val TIMER_DELAY = 300L
    }
}