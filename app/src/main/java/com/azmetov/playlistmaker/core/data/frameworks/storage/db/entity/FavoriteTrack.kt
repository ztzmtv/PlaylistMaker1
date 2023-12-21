package com.azmetov.playlistmaker.core.data.frameworks.storage.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class FavoriteTrack(
    @PrimaryKey(autoGenerate = false)
    val trackId: Int,
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя
    val trackTime: Int, // Продолжительность трека
    val artworkUrl100: String?, // Ссылка на изображение обложки
    val collectionName: String?,
    val releaseDate: String?,
    val genre: String?,
    val country: String?,
    val previewUrl: String?,
    val timeWhenAdded: Long,
)
