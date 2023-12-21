package com.azmetov.playlistmaker.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import com.azmetov.playlistmaker.core.data.frameworks.network.NetworkClient
import com.azmetov.playlistmaker.core.data.frameworks.network.NetworkClientImpl
import com.azmetov.playlistmaker.core.data.frameworks.storage.SharedPrefsStorage
import com.azmetov.playlistmaker.core.data.frameworks.storage.Storage
import com.azmetov.playlistmaker.core.data.frameworks.storage.db.TrackDatabase
import com.azmetov.playlistmaker.core.ui.recycler.BaseAdapter
import com.azmetov.playlistmaker.utils.Constants.SHARED_PREFS
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val baseModule = module {
    single<Gson> {
        Gson()
    }
    single<NetworkClient> {
        NetworkClientImpl(get())
    }
    factory<Storage> {
        SharedPrefsStorage(get(), get())
    }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
    }
    factory<Handler> {
        Handler(Looper.getMainLooper())
    }
    factory<MediaPlayer> {
        MediaPlayer()
    }
    factory(named(NAME_BASE_ADAPTER)) {
        BaseAdapter()
    }
    factory {
        Room
            .databaseBuilder(androidContext(), TrackDatabase::class.java, "database.db")
            .build()
            .getDao()
    }
}

const val NAME_BASE_ADAPTER = "NAME_BASE_ADAPTER"