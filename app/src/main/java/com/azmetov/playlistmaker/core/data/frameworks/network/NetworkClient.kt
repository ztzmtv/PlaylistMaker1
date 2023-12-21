package com.azmetov.playlistmaker.core.data.frameworks.network

import com.azmetov.playlistmaker.core.domain.models.NetworkResult

interface NetworkClient {
    suspend fun sendRequest(requestText: String): NetworkResult
}