package com.azmetov.playlistmaker.features.search.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.features.search.domain.NetworkInteractor
import com.azmetov.playlistmaker.features.search.domain.SearchStorageInteractor
import com.azmetov.playlistmaker.features.search.ui.state.SearchScreenState
import com.azmetov.playlistmaker.utils.Converter
import com.azmetov.playlistmaker.utils.debounce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchStorageInteractor: SearchStorageInteractor,
    private val networkInteractor: NetworkInteractor,
) : ViewModel() {
    private var textQuery = ""
    private val screenState = MutableLiveData<SearchScreenState?>()
    fun screenStateLiveData(): LiveData<SearchScreenState?> = screenState

    fun addToStorage(track: Track) {
        searchStorageInteractor.addToSharedPrefsUseCase(track)
    }

    fun updateScreen(state: SearchScreenState) {
        screenState.value = state
    }

    fun getListFromStorage(): List<Track>? {
        return searchStorageInteractor.getListFromStorageUseCase()
    }

    fun searchDebounce(text: String, scope: CoroutineScope) {
        if (text.length > 2) {
            textQuery = text
            debounce<Unit>(SEARCH_DEBOUNCE_DELAY, scope, false) { searchRequest() }.invoke(Unit)
        }
    }

    private fun searchRequest() {
        updateScreen(SearchScreenState.Loading)
        sendRequest()
    }

    fun clearListListener() {
        searchStorageInteractor.clearStorageUseCase()
        val emptyList = mutableListOf<Track>()
        updateScreen(SearchScreenState.History(emptyList))
    }

    fun sendRequest() {
        viewModelScope.launch {
            try {
                val result = networkInteractor.getListFromNetworkUseCase(textQuery)
                Log.d("SearchViewModelTAG", "sendRequest()")
                val screenState = Converter.networkToScreenState(result)
                updateScreen(screenState)
            } catch (t: Throwable) {
                updateScreen(SearchScreenState.ConnectionError)
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}