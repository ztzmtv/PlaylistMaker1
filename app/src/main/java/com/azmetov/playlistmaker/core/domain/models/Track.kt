package com.azmetov.playlistmaker.core.domain.models

import java.io.Serializable

typealias ListOfTracks = List<Track>

data class Track(
    val trackId: Int,
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя
    val trackTime: Int, // Продолжительность трека
    val artworkUrl100: String?, // Ссылка на изображение обложки
    val collectionName: String?,
    val releaseDate: String?,
    val genre: String?,
    val country: String?,
    val previewUrl: String?
) : Serializable