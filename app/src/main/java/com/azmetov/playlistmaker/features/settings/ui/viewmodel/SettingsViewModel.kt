package com.azmetov.playlistmaker.features.settings.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.azmetov.playlistmaker.App
import com.azmetov.playlistmaker.core.domain.models.ThemeSettings
import com.azmetov.playlistmaker.core.domain.sharing.SharingInteractor
import com.azmetov.playlistmaker.features.settings.domain.SettingsInteractor
import com.azmetov.playlistmaker.utils.Converter

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {
    var isDarkMode = false
        private set


    init {
        val settings = settingsInteractor.getThemeSettings()
        isDarkMode = Converter.convertThemeToBool(settings.theme)
    }

    fun setTheme(isDarkTheme: Boolean) {
        val theme = Converter.convertBoolToTheme(isDarkTheme)
        val settings = ThemeSettings(theme)
        settingsInteractor.updateThemeSetting(settings)
        App.instance.switchTheme(isDarkTheme)
    }

    fun shareApp() = sharingInteractor.shareApp()
    fun openTerms() = sharingInteractor.openTerms()
    fun openSupport() = sharingInteractor.openSupport()
}