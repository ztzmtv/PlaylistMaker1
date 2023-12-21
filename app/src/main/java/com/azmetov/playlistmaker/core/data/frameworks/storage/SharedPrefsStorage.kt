package com.azmetov.playlistmaker.core.data.frameworks.storage

import android.content.SharedPreferences
import com.azmetov.playlistmaker.core.domain.models.Theme
import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.utils.Constants
import com.azmetov.playlistmaker.utils.Constants.THEME_SWITCHER_KEY
import com.azmetov.playlistmaker.utils.Constants.TRACKS_HISTORY_KEY
import com.azmetov.playlistmaker.utils.Constants.TRACK_DATA_KEY
import com.azmetov.playlistmaker.utils.Converter.convertBoolToTheme
import com.azmetov.playlistmaker.utils.Converter.convertThemeToBool
import com.google.gson.Gson

class SharedPrefsStorage(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : Storage {
    private var trackList = mutableListOf<Track>()
    override fun saveSingleTrack(track: Track) {
        sharedPreferences
            .edit()
            .putString(TRACK_DATA_KEY, gson.toJson(track))
            .apply()
    }

    override fun loadSingleTrack(): Track? {
        val trackJson = sharedPreferences.getString(TRACK_DATA_KEY, "") ?: ""
        return gson.fromJson(trackJson, Track::class.java)
    }

    override fun addTrackToList(track: Track) {
        if (trackList.contains(track)) {
            trackList.remove(track)
        }
        trackList.add(track)
        cropList()
        val json = gson.toJson(trackList)
        sharedPreferences.edit().putString(TRACKS_HISTORY_KEY, json).apply()
    }

    override fun getTrackList(): List<Track>? {
        val json = sharedPreferences.getString(TRACKS_HISTORY_KEY, null) ?: return null
        trackList = gson.fromJson(json, Array<Track>::class.java).toMutableList()
        return trackList.reversed()
    }

    override fun clearTrackList() {
        trackList.clear()
        sharedPreferences.edit()
            .remove(TRACKS_HISTORY_KEY)
            .remove(TRACK_DATA_KEY)
            .apply()
    }

    override fun getTheme(): Theme {
        val isDarkMode = sharedPreferences.getBoolean(THEME_SWITCHER_KEY, false)
        return convertBoolToTheme(isDarkMode)
    }

    override fun setTheme(theme: Theme) {
        sharedPreferences.edit()
            .putBoolean(THEME_SWITCHER_KEY, convertThemeToBool(theme))
            .apply()
    }

    private fun cropList() {
        trackList = if (trackList.size > 10) {
            trackList.subList(0, Constants.COUNT_OF_TRACKS - 1)
        } else {
            trackList
        }
    }
}