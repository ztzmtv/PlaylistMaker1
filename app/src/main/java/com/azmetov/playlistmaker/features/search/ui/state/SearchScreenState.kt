package com.azmetov.playlistmaker.features.search.ui.state

import com.azmetov.playlistmaker.core.domain.models.Track

sealed class SearchScreenState {
    class Result(val list: List<Track>) : SearchScreenState()
    class History(val list: List<Track>) : SearchScreenState()
    object NotFound : SearchScreenState()
    object ConnectionError : SearchScreenState()
    object Loading : SearchScreenState()
}
