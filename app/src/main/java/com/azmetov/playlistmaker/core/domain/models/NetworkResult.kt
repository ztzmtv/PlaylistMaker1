package com.azmetov.playlistmaker.core.domain.models

sealed class NetworkResult {
    class Success<T>(val data: T) : NetworkResult()
    class Error(val message: String) : NetworkResult()
}