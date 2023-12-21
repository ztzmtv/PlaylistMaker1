package com.azmetov.playlistmaker.utils

import com.azmetov.playlistmaker.core.domain.models.NetworkResult
import com.azmetov.playlistmaker.core.data.frameworks.network.models.TrackDto
import com.azmetov.playlistmaker.core.data.frameworks.storage.db.entity.FavoriteTrack
import com.azmetov.playlistmaker.core.domain.models.ListOfTracks
import com.azmetov.playlistmaker.core.domain.models.Theme
import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.features.search.ui.state.SearchScreenState
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object Converter {

    fun dtoToEntity(dto: TrackDto) = Track(
        trackId = dto.trackId,
        trackName = dto.trackName,
        artistName = dto.artistName,
        trackTime = dto.trackTimeMillis,
        artworkUrl100 = dto.artworkUrl100,
        collectionName = dto.collectionName,
        releaseDate = getYearFromTimestamp(dto.releaseDate),
        genre = dto.genre,
        country = dto.country,
        previewUrl = dto.previewUrl
    )

    fun entityToFavoriteDbModel(model: Track) = FavoriteTrack(
        trackId = model.trackId,
        trackName = model.trackName,
        artistName = model.artistName,
        trackTime = model.trackTime,
        artworkUrl100 = model.artworkUrl100,
        collectionName = model.collectionName,
        releaseDate = model.releaseDate,
        genre = model.genre,
        country = model.country,
        previewUrl = model.previewUrl,
        timeWhenAdded = System.currentTimeMillis()
    )

    fun favoriteDbModelToEntity(dbModel: FavoriteTrack) = Track(
        trackId = dbModel.trackId,
        trackName = dbModel.trackName,
        artistName = dbModel.artistName,
        trackTime = dbModel.trackTime,
        artworkUrl100 = dbModel.artworkUrl100,
        collectionName = dbModel.collectionName,
        releaseDate = dbModel.releaseDate,
        genre = dbModel.genre,
        country = dbModel.country,
        previewUrl = dbModel.previewUrl
    )

    fun convertTime(time: Int?): String =
        SimpleDateFormat(TRACK_TIME_PATTERN, Locale.getDefault())
            .format(time?.toLong() ?: TRACK_TIME_IF_NULL)

    private fun getYearFromTimestamp(timestamp: String?): String {
        return if (timestamp != null) {
            LocalDateTime
                .parse(timestamp, DateTimeFormatter.ofPattern(DATETIME_PATTERN))
                .year
                .toString()
        } else DATETIME_IF_NULL
    }

    fun getCoverArtwork(artworkUrl100: String?) =
        artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg")

    fun networkToScreenState(networkResult: NetworkResult): SearchScreenState {
        return when (networkResult) {
            is NetworkResult.Error -> SearchScreenState.ConnectionError
            is NetworkResult.Success<*> -> {
                val data = networkResult.data as ListOfTracks
                if (data.isNotEmpty()) {
                    SearchScreenState.Result(data)
                } else {
                    SearchScreenState.NotFound
                }
            }
        }
    }

    fun convertThemeToBool(theme: Theme): Boolean {
        return when (theme) {
            Theme.LIGHT -> false
            Theme.DARK -> true
        }
    }

    fun convertBoolToTheme(isDarkMode: Boolean): Theme {
        return when (isDarkMode) {
            true -> Theme.DARK
            false -> Theme.LIGHT
        }
    }

    private const val DATETIME_IF_NULL = ""
    private const val TRACK_TIME_IF_NULL = 0
    private const val DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssz"
    private const val TRACK_TIME_PATTERN = "mm:ss"

}