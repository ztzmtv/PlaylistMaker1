package com.azmetov.playlistmaker.di

import com.azmetov.playlistmaker.features.search.domain.SearchStorageInteractor
import com.azmetov.playlistmaker.features.search.domain.SearchStorageInteractorImpl
import com.azmetov.playlistmaker.features.search.data.SearchRepositoryImpl
import com.azmetov.playlistmaker.features.search.domain.NetworkInteractor
import com.azmetov.playlistmaker.features.search.domain.NetworkInteractorImpl
import com.azmetov.playlistmaker.features.search.domain.SearchRepository
import org.koin.dsl.module

val searchModule = module {
    factory<SearchStorageInteractor> {
        SearchStorageInteractorImpl(get())
    }
    factory<NetworkInteractor> {
        NetworkInteractorImpl(get())
    }
    single<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }
}