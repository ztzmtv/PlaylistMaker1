package com.azmetov.playlistmaker.features.search.domain

import com.azmetov.playlistmaker.core.domain.models.NetworkResult

interface NetworkInteractor {
    suspend fun getListFromNetworkUseCase(text: String): NetworkResult
}