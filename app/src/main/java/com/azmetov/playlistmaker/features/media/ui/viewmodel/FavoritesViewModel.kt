package com.azmetov.playlistmaker.features.media.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.azmetov.playlistmaker.features.media.domain.GetFavoritesUseCase
import kotlinx.coroutines.Dispatchers

class FavoritesViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {
    fun favoritesList() = getFavoritesUseCase.getFavoritesUseCase().asLiveData(Dispatchers.IO)
}
