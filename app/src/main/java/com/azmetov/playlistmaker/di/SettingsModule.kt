package com.azmetov.playlistmaker.di

import com.azmetov.playlistmaker.core.data.sharing.ExternalNavigatorImpl
import com.azmetov.playlistmaker.core.domain.sharing.ExternalNavigator
import com.azmetov.playlistmaker.core.domain.sharing.SharingInteractor
import com.azmetov.playlistmaker.core.domain.sharing.SharingInteractorImpl
import com.azmetov.playlistmaker.features.settings.data.SettingsRepositoryImpl
import com.azmetov.playlistmaker.features.settings.domain.SettingsInteractor
import com.azmetov.playlistmaker.features.settings.domain.SettingsInteractorImpl
import com.azmetov.playlistmaker.features.settings.domain.SettingsRepository
import org.koin.dsl.module

val settingsModule = module {
    factory<SharingInteractor> {
        SharingInteractorImpl(get())
    }
    factory<ExternalNavigator> {
        ExternalNavigatorImpl(get())
    }
    factory<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }
    factory<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }
}