package com.azmetov.playlistmaker.di

import com.azmetov.playlistmaker.features.player.data.FavoritesRepositoryImpl
import com.azmetov.playlistmaker.features.player.data.PlayerRepositoryImpl
import com.azmetov.playlistmaker.features.player.data.player.PlayerManager
import com.azmetov.playlistmaker.features.player.data.player.PlayerManagerImpl
import com.azmetov.playlistmaker.features.player.domain.FavoritesRepository
import com.azmetov.playlistmaker.features.player.domain.PlayerInteractor
import com.azmetov.playlistmaker.features.player.domain.PlayerInteractorImpl
import com.azmetov.playlistmaker.features.player.domain.PlayerRepository
import com.azmetov.playlistmaker.features.player.domain.PlayerStorageInteractor
import com.azmetov.playlistmaker.features.player.domain.PlayerStorageInteractorImpl
import org.koin.dsl.module

val playerModule = module {
    factory<PlayerStorageInteractor> {
        PlayerStorageInteractorImpl(get(), get())
    }
    factory<PlayerInteractor> {
        PlayerInteractorImpl(get())
    }

    factory<PlayerRepository> {
        PlayerRepositoryImpl(get(), get())
    }

    factory<PlayerManager> {
        PlayerManagerImpl(get())
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get())
    }
}