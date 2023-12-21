package com.azmetov.playlistmaker.di

import com.azmetov.playlistmaker.features.media.domain.GetFavoritesUseCase
import com.azmetov.playlistmaker.features.media.domain.GetFavoritesUseCaseImpl
import org.koin.dsl.module

val mediaModule = module {
    factory<GetFavoritesUseCase> { GetFavoritesUseCaseImpl(get()) }
}