package com.azmetov.playlistmaker.core.data.frameworks.storage

import com.azmetov.playlistmaker.core.domain.models.Theme
import com.azmetov.playlistmaker.core.domain.models.Track

interface Storage {
    fun saveSingleTrack(track: Track)
    fun loadSingleTrack(): Track?
    fun addTrackToList(track: Track)
    fun getTrackList(): List<Track>?
    fun clearTrackList()
    fun getTheme(): Theme
    fun setTheme(theme: Theme)
}