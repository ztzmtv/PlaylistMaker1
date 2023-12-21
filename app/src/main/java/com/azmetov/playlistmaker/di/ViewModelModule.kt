package com.azmetov.playlistmaker.di

import com.azmetov.playlistmaker.features.media.ui.viewmodel.FavoritesViewModel
import com.azmetov.playlistmaker.features.media.ui.viewmodel.PlaylistsViewModel
import com.azmetov.playlistmaker.features.player.ui.viewmodel.PlayerViewModel
import com.azmetov.playlistmaker.features.search.ui.viewmodel.SearchViewModel
import com.azmetov.playlistmaker.features.settings.ui.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SettingsViewModel(get(), get())
    }
    viewModel {
        SearchViewModel(get(), get())
    }
    viewModel {
        PlayerViewModel(get(), get())
    }
    viewModel {
        FavoritesViewModel(get())
    }
    viewModel {
        PlaylistsViewModel()
    }
}