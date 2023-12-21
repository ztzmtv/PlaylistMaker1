package com.azmetov.playlistmaker.features.settings.domain

import com.azmetov.playlistmaker.core.domain.models.ThemeSettings

interface SettingsInteractor {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(settings: ThemeSettings)
}