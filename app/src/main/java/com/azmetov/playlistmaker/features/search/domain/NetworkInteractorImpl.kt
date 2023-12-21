package com.azmetov.playlistmaker.features.search.domain

import com.azmetov.playlistmaker.core.domain.models.NetworkResult

class NetworkInteractorImpl(
    private val searchRepository: SearchRepository
) : NetworkInteractor {

    override suspend fun getListFromNetworkUseCase(text: String): NetworkResult {
        return searchRepository.sendRequest(text)
    }

}