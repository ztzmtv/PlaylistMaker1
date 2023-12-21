package com.azmetov.playlistmaker.core.data.frameworks.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.azmetov.playlistmaker.core.domain.models.NetworkResult
import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.utils.Converter

class NetworkClientImpl(
    private val context: Context
) : NetworkClient {

    override suspend fun sendRequest(requestText: String): NetworkResult {
        val response = ApiFactory.apiService.search(requestText)
        if (!response.isSuccessful) {
            return NetworkResult.Error(message = response.message() ?: EMPTY_STRING)
        }
        when (response.code()) {
            in 200..299 -> {
                if (response.body()?.resultCount == 0)
                    return NetworkResult.Success(EMPTY_LIST)
                else
                    response.body()?.results?.map { Converter.dtoToEntity(it) }?.apply {
                        return NetworkResult.Success(this)
                    }
            }
            else -> return NetworkResult.Error(EMPTY_STRING)
        }
        return NetworkResult.Error(EMPTY_STRING)
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }

    companion object {
        private const val EMPTY_STRING = ""
        private val EMPTY_LIST = listOf<Track>()
    }
}