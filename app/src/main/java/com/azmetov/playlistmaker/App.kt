package com.azmetov.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.azmetov.playlistmaker.di.baseModule
import com.azmetov.playlistmaker.di.mediaModule
import com.azmetov.playlistmaker.di.mediaUiModule
import com.azmetov.playlistmaker.di.playerModule
import com.azmetov.playlistmaker.di.playerUiModule
import com.azmetov.playlistmaker.di.searchModule
import com.azmetov.playlistmaker.di.searchUiModule
import com.azmetov.playlistmaker.di.settingsModule
import com.azmetov.playlistmaker.di.viewModelModule
import com.azmetov.playlistmaker.utils.Constants.SHARED_PREFS
import com.azmetov.playlistmaker.utils.Constants.THEME_SWITCHER_KEY
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    private var darkTheme = false

    override fun onCreate() {
        super.onCreate()
        instance = this

        createObjects()
        switchTheme(darkTheme)

        startKoin {
            androidContext(this@App)
            androidLogger(Level.INFO)
            modules(
                baseModule,
                viewModelModule,
                settingsModule,
                searchModule,
                searchUiModule,
                playerModule,
                playerUiModule,
                mediaUiModule,
                mediaModule
            )
        }
    }

    private fun createObjects() {
        val themePrefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        darkTheme = themePrefs.getBoolean(THEME_SWITCHER_KEY, false)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    companion object {
        lateinit var instance: App
            private set
    }
}