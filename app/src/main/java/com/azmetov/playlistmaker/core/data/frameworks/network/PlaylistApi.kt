package com.azmetov.playlistmaker.core.data.frameworks.network

import com.azmetov.playlistmaker.core.data.frameworks.network.models.PlaylistResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaylistApi {

    @GET("search")
    suspend fun search(
        @Query("term") text: String,
        @Query("entity") type: String = "song"
    ): Response<PlaylistResponseDto>
}